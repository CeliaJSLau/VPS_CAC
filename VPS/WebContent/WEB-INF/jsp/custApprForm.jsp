<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="javax.servlet.*"%>
<%@page import="javax.servlet.http.*"%>


<!DOCTYPE html>
<html lang="en">

<meta charset="utf-8" content="text/html;">
<meta name="viewport" http-equiv="Content-Type"  content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=11">
<link href="css/jquery-ui.min.css" rel="stylesheet" type="text/css" />
<style>
body{
  align:center;
}
img {
  width: 100%;
  height: auto;
  max-width:377px; 
}
.header{
  text-align:center;
  margin-left: auto; 
  margin-right: auto; 
}
.content {
  max-width: 1000px;
  margin: auto;
  background: white;
  padding: 0px 10px 10px 10px;
}
.sh4 {
  display: block;
  font-size: 1em;
  margin-top: 1.33em;
  margin-bottom: 1.33em;
  margin-left: 0;
  margin-right: 0;
  font-weight: bold;
}

p.cell {border-style: groove; background-color: #f1f1f1;padding: 0.5em;margin-bottom:-1em;}
</style>
<script  type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<script  type="text/javascript" src="js/jquery-ui.min.js"></script>
<script  type="text/javascript">
function validateVenue(){
	if(document.getElementById("wd").value == ""){
		alert("請輸入場地名稱\nPlease input the name of venue");				
// 		 event.preventDefault();
		return false;
	}
}
function validate(){

	<%if (request.getParameter("qrParam") == null ) {%>

	validateVenue();
	if((document.getElementById("wd").value !=null && document.getElementById("wd").value != "")
			&& ($("#id").val()==null ||$("#id").val()=="")|| document.getElementById("wd").value!=$("#label").val()){
		alert("請輸入正確場地名稱\nPlease enter the right name of venue");		
		event.preventDefault();
		return false;
	}

	<%}%>
	var obj = document.getElementsByName("category");

	if(!(obj[0].checked||obj[1].checked||obj[2].checked)){
		alert("請在適當方格內加上「✔」\nPlease put a “✔” in the appropriate box(es)");
		event.preventDefault();
		return false;
	}

}
$(function(){

	<%if (request.getParameter("qrParam") != null && request.getParameter("qrParam") != "") {%>
		document.getElementById('selectedVenue').style.display = "inline";
		document.getElementById('selectVenue').style.display = "none";
	<%} else {%>
		document.getElementById('wd').innerHTML="";
		document.getElementById('selectedVenue').style.display = "none";
		document.getElementById('selectVenue').style.display = "inline";
	<%}%>
	
	$( "#wd" ).autocomplete({
		source: function( request, response ) {
             $.ajax( {
            	 type: 'POST',
               url: "search?searchKW="+encodeURI($("#wd").val()),
//                url: "${pageContext.request.contextPath}/search?searchKW="+$("#wd").val(),
               dataType: "json",
               data: {
            	   id:$("#id").val(),
            	   label:$("#wd").val()               
               },
               contentType: "application/json; charset=utf-8",
	              success: function( data ) {
	            	   $.each(data, function(i, obj){
// 	            		   alert(obj.label);
	            	   });
	            	   
	                  	response( data );
	                }
           })
       },    
       minChars : 1,
       select: function( event, ui ) {
       		$("#wd").val(ui.item.label);
			$("#id").val(ui.item.id);
			document.getElementById('selectedVenue').style.display="inline";
			if(ui.item.locale =="VenueEng"){
				document.getElementById('loc').innerHTML=ui.item.defaultName+" "+ui.item.label;
			}else{
				document.getElementById('loc').innerHTML=ui.item.label+" "+ui.item.defaultName;
			}		
			
			$("#sloc").val(ui.item.label+" "+ui.item.defaultName);
			$("#label").val(ui.item.label);
       }
   });
	

});
function openOptionalForm(){
	if(document.getElementById('optional').style.display=="none")
		document.getElementById('optional').style.display="inline";
	else if(document.getElementById('optional').style.display=="inline")
		document.getElementById('optional').style.display="none";
}

</script>

<head>
<title>Customer Appreciation Card</title>
</head>
<body>
<div class="header" role="banner">
	<img class="logo" src="image/LCSD WEB LOGO.png" alt="LCSD web logo photo 康文署网页標識图"  width="377" height="175" />
</div>
<div class="content">
<!-- <p><img class="logo" src="image/lcsd_logo.png" alt="LCSD"  width="538" height="250"/></p> -->
<h1 style="text-align: center;font-size:1.5em;"><strong>顧客讚賞卡 </strong></h1>
<h1 style="text-align: center;font-size:1.5em;"><strong>Customer Appreciation Card</strong></h1>

<p></p>
<form method="post" action="CAC"  onsubmit="validate()" id="CAC">


<p></p>

<div id="selectVenue">

<label for="wd" class="sh4"><strong>你想讚賞的場地 You want to compliment on :</strong></label>
<input name="keyword" id= "wd" style=" width: 98%;font-size:1.5em; box-sizing: border-box;padding: 5px;" onblur="validateVenue()" placeholder="請輸入關鍵字搜索場地 Please enter keyword to search the venue" spellcheck="false"></input>
<input id="id" name="sid" type="hidden" />
<input id="label" name="slabel" type="hidden" />
<input id="sloc" name="sloc" type="hidden" />
</div>
<p></p>

<div id="selectedVenue">
<p><span id="loc" style="color:#f05828;font-weight:bold;font-size:1.5em"><c:out value="${location}"/></span></p>

</div>
	
			<fieldset style="border: 0;">
			<legend style="margin-left:-0.5em;padding: 5px;"><em>請在適當方格內加上<span>「</span></em>✔<em><span></span></em><em><span>」，並可選多於一項。</span>Please put a "✔" in the appropriate box(es). You may select more than one item.</em></legend>
			<div class="checkCat" style="box-sizing: border-box;padding: 5px;margin:5px 5px 5px 5px;font-size:0.9em" >
			<input type="checkbox" name="category" value="Staff" id="cat1"/>
			<label for="cat1"><strong>員工<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Staff</strong></label><br/><br/>
			<input type="checkbox" name="category" value="Venue Environent and Facilities" id="cat2"/>
			<label for="cat2"><strong>場地環境及/或設施<br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Venue Environment and/or Facilities</strong></label><br/><br/>
			<input type="checkbox" name="category" value="Programmes/Events" id="cat3"/>
			<label for="cat3"><strong>節目及/或活動<br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Programmes and/or Events</strong></label><br/>
			</div>
			</fieldset>
			

			<input type="button" onclick="openOptionalForm()" value="選填 Optional"style="padding: 2px 2px;border-radius: 5px;margin-left:1em; font-size:0.9em;margin-top:0.7em;margin-bottom:0.7em"/>
			<div id="optional" style="display:none"> 
			<br/><br/>
			
			<p class = "cell">
			<label for="fb" >讚賞詳情 Compliment Details</label><br/>
			<textarea rows="5" style="width: 98%;box-sizing: border-box; font-size:1.5em;margin-right:0.5em;margin-top:0.3em" name="feedback" id= "fb" maxlength="400" spellcheck="false"></textarea>
			<br/>
			</p>
			</div>
<br/><br/>

<!-- 			<button type="submit" value="SUBMIT 遞交" form="CAC" style="background-color: #f05828; color:#fff; display: inline-block; padding: 2px 8px; margin-top:1em;margin-bottom:1em;font-weight: bold; border-radius: 5px;font-size:1em">遞交 SUBMIT </button>	 -->
			<button type="submit" value="SUBMIT 遞交" form="CAC" style=" display: inline-block; padding: 2px 8px; margin-top:1em;margin-bottom:1em;font-weight: bold; border-radius: 5px;font-size:1em">遞交 SUBMIT </button>
</form>
</div>
</body>
</html>