/*Object.prototype.counts = function() {
			var that = this,
				count = 0;

			for(property in that) {
				if(that.hasOwnProperty(property)) {
					count++;
				}
			}

			return count;
		   };
		   
		   function isEmpty(object) { 
		        for(var i in object) 
				{ 
				    return true; 
			    } 
				return false; 
			} */
   // approve session
   function approvesession(){  
	   var jsonData = new Object();
       jsonData.taskName = "approveSession";
       
       var jsonTaskData = new Object();
       jsonTaskData.id =  $(this).attr("id");
       jsonTaskData.comment = escape($("#textarea").val());
       jsonTaskData.status = $(this).val().split(" ")[0].toLowerCase() + "d";
		
		//meaning we are expecting JSON data in response from the server
		$.ajax({
			type: "POST",
			url: 'task.is',
			data: {jsonData:JSON.stringify(jsonData), jsonTaskData:JSON.stringify(jsonTaskData)},
			dataType: "json",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			
			
			//if received a response from the server
			success: function( data, textStatus, jqXHR) {
				//our country code was correct so we have some information to display
				 if(data.success){
					 $("#success").html("Successfully approved session");
				 }
				 //display error message
				 else {
					 $("#success").html("Unsuccessfully approved session");
				 }
			},
			
			//If there was no resonse from the server
			error: function(jqXHR, textStatus, errorThrown){
			     $("#success").html("Unsuccessfully approved session");
				 //console.log("Something really bad happened " + textStatus);
				 // $("#ajaxResponse").html(jqXHR.responseText);
			},
			
			//capture the request before it was sent to server
			beforeSend: function(jqXHR, settings){
				//adding some Dummy data to the request
				//settings.data += "&dummyData=addFacility";
				//disable the button until we get the response
				$('#myButton').attr("disabled", true);
			},
			
			//this is called after the response or error functions are finsihed
			//so that we can take some action
			complete: function(jqXHR, textStatus){
				//enable the button
				$('#myButton').attr("disabled", false);
			}
  
		});  
   };  
		
   // save session	
   function savesession(){                     
	   //table to json
		var myRows = {measurementData:[]};
		var $th = $('table th');
		
		var colNum = $("#myTable table th").length - 1;
		for(var i = 1; i < colNum; i++){
		   var col = [];
		   var obj = {};
		   $tds = $("table td.column" + i.toString());  
		   id = $("table th.column" + i.toString()).attr("id");
		   $tds.each(function(index, td){
				col.push(parseFloat($(td).text()));				
				//obj[i] = $tds.eq(index).text();
		   });
			
			//myRows["measurementData"] = [];
			var clan = {};
			clan.values = col;
			clan.measureId = id;
			myRows.measurementData.push(clan);
			//myRows[i].values = col;
			//myRows[i].measureId = id;	myRows.measurementData.push			
		}
					
		//alert($("#dropdown option:selected").val());
		myRows["sessionTemplateId"] = $("#dropdown option:selected").val();
		deviceId = $("#id").val();			
		myRows["deviceId"] = deviceId; //** dodato
		
		var jsonData = '{"taskName":"saveSession"}';
		var jsonTaskData = JSON.stringify(myRows);
		
		//meaning we are expecting JSON data in response from the server
		$.ajax({
			type: "POST",
			url: 'task.is',
			data: "jsonData=" + jsonData + "&jsonTaskData=" + jsonTaskData,
			dataType: "json",
			
			//if received a response from the server
			success: function( data, textStatus, jqXHR) {
				//our country code was correct so we have some information to display
				 if(data.success){
					 $("#success").html("Successfully saved session");
				 }
				 //display error message
				 else {
					 $("#success").html("Unsuccessfully saved session");
				 }
				 
				 var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			     var sNodes = treeObj.getSelectedNodes();
				 onClick("click",treeObj,sNodes[0],1);
				 //$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			},
			
			//If there was no resonse from the server
			error: function(jqXHR, textStatus, errorThrown){
			     $("#success").html("Unsuccessfully saved session");
				 //console.log("Something really bad happened " + textStatus);
				 // $("#ajaxResponse").html(jqXHR.responseText);
			},
			
			//capture the request before it was sent to server
			beforeSend: function(jqXHR, settings){
				//adding some Dummy data to the request
				//settings.data += "&dummyData=addFacility";
				//disable the button until we get the response
				$('#myButton').attr("disabled", true);
			},
			
			//this is called after the response or error functions are finsihed
			//so that we can take some action
			complete: function(jqXHR, textStatus){
				//enable the button
				$('#myButton').attr("disabled", false);
			}
  
		});  
   }; 
   
   // delete
   function deleteSession(){  
	   var jsonData = new Object();
       jsonData.taskName = "deleteSession";
       
       var jsonTaskData = new Object();
       jsonTaskData.id =  $(this).attr("id");
       //jsonTaskData.comment = escape($("#textarea").val());
       //jsonTaskData.status = $(this).val().split(" ")[0].toLowerCase() + "d";
		
		//meaning we are expecting JSON data in response from the server
		$.ajax({
			type: "POST",
			url: 'task.is',
			data: {jsonData:JSON.stringify(jsonData), jsonTaskData:JSON.stringify(jsonTaskData)},
			dataType: "json",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',			
			
			//if received a response from the server
			success: function( data, textStatus, jqXHR) {
				//our country code was correct so we have some information to display
				 if(data.success){
					 $("#success").html("Successfully deleted session");
					 //oldSessions(json);
				 }
				 //display error message
				 else {
					 $("#success").html("Unsuccessfully deleted session");
				 }
				 
				 var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			     var sNodes = treeObj.getSelectedNodes();
				 onClick("click",treeObj,sNodes[0],1);
			},
			
			//If there was no resonse from the server
			error: function(jqXHR, textStatus, errorThrown){
			     $("#success").html("Unsuccessfully approved session");
				 //console.log("Something really bad happened " + textStatus);
				 // $("#ajaxResponse").html(jqXHR.responseText);
			},
  
		});  
   };  
			
			
   function jsontotable(data, kind){
	   json = data;
	   var numCol = json.headerInfos.length;
	   header = json.headerInfos;
	   data = json.values;
	   var div; //= document.getElementById("myTable");
	   
	   if(kind == "nominal"){
	     type = "column_n";
		 div = document.getElementById("nominalTable");
		 $("#nominalTable").empty();
	   }
	   else{
	     type = "column";
		 div = document.getElementById("myTable");
		 $("#myTable").empty();
	   }
	   
	   var broj = 0;
	   for (key in data) {
				if (data.hasOwnProperty(key)) {
					 broj++;
				 }
	   }

	   var table = document.createElement('table');
	   table.id = "excel";
	   //var div = document.getElementById("myTable");
	   //$("#myTable").empty();
	   div.appendChild(table); // Append it
	   // thead node
	   var thead = document.createElement('thead');
	   table.appendChild(thead); // Append it
	   var tbody = document.createElement('tbody');
	   table.appendChild(tbody); // Append it
	   // table header
	   
	   th = document.createElement('th');
	   th.setAttribute("colspan", numCol+1); 
	   if(kind == "nominal")
	      th.innerHTML = "Nominal values";
	  else
	      th.innerHTML = "Recorded values";
	   thead.appendChild(th); // Append it
	   
       var tr; 	   
	   var th;
	   var colh = 1;
	   // order number in header
	   tr = document.createElement('tr');
	   th = document.createElement('th');
	   th.innerHTML = "#";
	   th.className = type + "0";
	   tr.appendChild(th);
	   
	   for (j=0; j<numCol;j++) {
			mUnit = "";
			th = document.createElement('th');
			if (header[j].measureUnit != null)
			   mUnit = " (" + header[j].measureUnit +")";
			th.innerHTML = header[j].measureName + mUnit;
			th.id = header[j].measureId;
			th.className = type + colh;
			tr.appendChild(th);
			//thead.appendChild(th); // Append it
			colh++;
	   }
	   thead.appendChild(tr);       
	   // table data
	   var td;
	   for (i=0; i<broj;i++){
		   tr = document.createElement('tr');
		   td = document.createElement('td');
		   td.innerHTML = i+1;
		   td.className= type + "0";
		   tr.appendChild(td); // Append it
		   var col = 1;
		   for (j=0; j<numCol;j++) {
				td = document.createElement('td');
				td.innerHTML = data[i][j];
				td.className = type + col;
				tr.appendChild(td); // Append it
				col++;
		   }
		   
		  tbody.appendChild(tr); // Append it		   
	  } 

	    // add new div for buttons
		if ($.isEmptyObject($.find('#buttons'))){
	       div = document.createElement("div");
		   div.id = "buttons";
		   div.style.display = "none"; //css("display", "none");
		   //div.css("margin-bottom", "20px");
		   $("#excel").after(div);
		 }else {
		   $("#buttons").empty();
		 }
	     //if($("#user-type").text()=="Supervisor"){
		 // textarea
		 textarea = document.createElement('textarea');		 
		 textarea.setAttribute("rows", 5);
		 textarea.setAttribute("cols", 50);
		 textarea.setAttribute("class", "comment");
		 textarea.setAttribute("id", "textarea");
		 //if(kind == "upload")
			$("#buttons").append(textarea);
		 //approve session button
		 input = document.createElement('input');
	     input.setAttribute("type", "button");
	     input.setAttribute("id", "approveButton");
	     input.setAttribute("class", "button");
		 input.setAttribute("value", "Approve session");
		 input.setAttribute("size","50")
	     input.addEventListener("click", approvesession);
		 //if(kind == "upload")
		    $("#textarea").after(input);
		 // decline sesssion button
		 input = document.createElement('input');
	     input.setAttribute("type", "button");
	     input.setAttribute("id", "declineButton");
		 input.setAttribute("class", "button");
		 input.setAttribute("value", "Decline session");
		 input.setAttribute("size","50")
	     input.addEventListener("click", approvesession);
		 //if(kind == "upload")
		    $("#approveButton").after(input);
	     //}
	     //else{
	     // save session button
		 input = document.createElement('input');
	     input.setAttribute("type", "button");
	     input.setAttribute("id", "sessionButton");
	     input.setAttribute("class", "button");
	     input.setAttribute("value", "Save session");
	     input.addEventListener("click", savesession);
		 input.style.display = "none"; // css("display", "none");
		 //if(kind == "upload")
	        $("#excel").after(input);
	     //}
   }