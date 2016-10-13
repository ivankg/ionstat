$(document).ready(function() {
 
    //Stops the submit request
    $("#myAjaxRequestForm").submit(function(e){
           e.preventDefault();
    });
    
    //checks for the button click event
    $("#myButton").click(function(e){
                      
            //get the form data using another method
			var facility = $("input#facilityName").val();
			var city = $("input#city").val();
			var pib = $("input#pib").val();
			
			//dataString = '{"taskName":"addNewFacility", "taskData": {"facilityName":' + '"' + facility
			//+ '", "city":"' + city + '", "pib":"' + pib + '"}}';
			//document.write(dataString);
			
			var jsonData = '{"taskName":"addFacility"}';
			var jsonTaskData = '{"facilityName":' + '"' + facility + '", "city":"' + city + '", "pib":"' + pib + '"}';
            //var jsonTaskData = '{"facilityName":"pera", "city":"pera", "pib":"123"}';
			
            //make the AJAX request, dataType is set to json
            //meaning we are expecting JSON data in response from the server
            $.ajax({
                type: "POST",
                url: $("#adresa").val(),
                data: "jsonData=" + jsonData + "&jsonTaskData=" + jsonTaskData,
                dataType: "json",
                
                //if received a response from the server
                success: function( data, textStatus, jqXHR) {
                    //our country code was correct so we have some information to display
                     if(data.success){
                         $("#ajaxResponse").html("");
                         $("#ajaxResponse").append("<b>Country Code:</b>");
                         $("#ajaxResponse").append("<b>Country Name:</b>");
                     }
                     //display error message
                     else {
                         $("#ajaxResponse").html("<div><b>Country code in Invalid!</b></div>");
                     }
                },
                
                //If there was no resonse from the server
                error: function(jqXHR, textStatus, errorThrown){
                     console.log("Something really bad happened " + textStatus);
                      $("#ajaxResponse").html(jqXHR.responseText);
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
    });
	
	//checks for the button click event // Convert table to json file
    /* $("#ttj").click(function(e){                     
           //table to json
		    var myRows = {measurementData:[]};
			var $th = $('table th');
            
			var colNum = $("table th").length - 1;
			for(var i = 1; i <= colNum; i++){
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
						
			myRows["sessionTemplateId"] = 1;
			//deviceId = $("#id").val();			
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
                         $("#ajaxResponse").html("");
                         $("#ajaxResponse").append("<b>Country Code:</b>");
                         $("#ajaxResponse").append("<b>Country Name:</b>");
                     }
                     //display error message
                     else {
                         $("#ajaxResponse").html("<div><b>Country code in Invalid!</b></div>");
                     }
                },
                
                //If there was no resonse from the server
                error: function(jqXHR, textStatus, errorThrown){
                     console.log("Something really bad happened " + textStatus);
                      $("#ajaxResponse").html(jqXHR.responseText);
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
	});*/	

});