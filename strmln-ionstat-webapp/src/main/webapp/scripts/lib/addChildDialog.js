$(function() {
	$("#closeBtn_addChildDialog").click(function(button) {
		$('#addChildDialog').hide();
		$('#mask').fadeOut();
	});
	$("#cancelBtn_addChildDialog").click(function(button) {
		$('#addChildDialog').hide();
		$('#mask').fadeOut();
	});
	$("#confirmButton_addChildDialog").click(function(button) {
		var selectedNodeId = $("#tree").dynatree("getTree").activeNode.data.id
 		var jsonData = {};
		jsonData.taskName = getTaskName()
		var jsonTaskData = {};
		jsonTaskData['parentId'] = selectedNodeId
		var item = 0;
		var childNodesArray;
		while(childNodesArray = $('#addChildDialog')[0].childNodes[item]){
			if(childNodesArray.className && childNodesArray.className == 'items'){
				jsonTaskData[childNodesArray.childNodes[1].id] = childNodesArray.childNodes[1].value
			}
		item++
		}
		addNewChiled(jsonData, jsonTaskData)
		$('#addChildDialog').hide();
		$('#mask').fadeOut();
	})
	
});

function addChildButtonMethod(){
		var selectedNodeLevel = $("#tree").dynatree("getTree").activeNode.getLevel();
		var propertiesArray;
		switch (selectedNodeLevel){
		case 1:
		  propertiesArray = [{
				"name" : "Name",
				"type" : "string",
				"postName" : "name"
			}, {
				"name" : "City",
				"type" : "string",
				"postName" : "city"
			}, {
				"name" : "Address",
				"type" : "string",
				"postName" : "address"
			}, {
				"name" : "Contact Person",
				"type" : "string",
				"postName" : "contactPerson"
			}, {
				"name" : "Email",
				"type" : "string",
				"postName" : "email"
			}, {
				"name" : "Phone",
				"type" : "number",
				"postName" : "phone"
			}, {
				"name" : "Pib",
				"type" : "number",
				"postName" : "pib"
			}];
		  createAddChildDialog(propertiesArray);
		  break;
		case 2:
		  propertiesArray = [{
				"name" : "Name",
				"type" : "string",
				"postName" : "name"
			}];;
		    createAddChildDialog(propertiesArray);
		  break;
		case 3:
		  propertiesArray = [{
				"name" : "Name",
				"type" : "string",
				"postName" : "name"
			}, {
				"name" : "Inspection Int",
				"type" : "string",
				"postName" : "inspectionInternal"
			}];;
		    createAddChildDialog(propertiesArray);				  
		  break;
		case 4:
		  propertiesArray = [{
				"name" : "Name",
				"type" : "string",
				"postName" : "name"
			}, {
				"name" : "Manufacturer",
				"type" : "string",
				"postName" : "manufacturer"
			}, {
				"name" : "Modal",
				"type" : "string",
				"postName" : "modal"
			}, {
				"name" : "Serial Number",
				"type" : "string",
				"postName" : "serialNumber"
			}, {
				"name" : "Device Usage",
				"type" : "string",
				"postName" : "deviceUsage"
			}];;
		    createAddChildDialog(propertiesArray);	  
		  break;
		}
	}

function addNewChiled(jsonData, jsonTaskData) {
	$.ajax({
		type : "POST",
		url : 'task.is',
		data : {jsonData:JSON.stringify(jsonData), jsonTaskData:JSON.stringify(jsonTaskData)},
		dataType : "json",
		success : function(data) {
			var node = $("#tree").dynatree("getActiveNode");
			onExpand(true, node)
		}	
	});
};

function onExpand(flag, node) {
	node.removeChildren();
	if(flag){
		var level = node.getLevel();
		var task = "";
		switch (level)
		{
			case 1:
			  task = "getFacilities";
			  break;
			case 2:
			  task = "getDepartments";				  
			  break;
			case 3:
			  task = "getRooms";			  
			  break;
			case 4:
			  task = "getDevices";			 
			  break;
		} 
		var id = node.data.id;	
		var jsonData = '{"taskName":"' + task + '"}';
		var jsonTaskData = '{"parentId":"	' + id + '"}';
		$.ajax({
			type : "GET",
			url : 'task.is',
			data : "jsonData=" + jsonData + "&jsonTaskData=" + jsonTaskData,
			dataType : "json",
			success : function(data) {
				var item = 0;
				while(data.taskResponse.entities && data.taskResponse.entities[item]){
					var currentEntities = data.taskResponse.entities[item]
					node.addChild({
						title: currentEntities.name,
						id: currentEntities.id,
						children: level == 4 ? null : [{}],
						isFolder : level == 4 ? false : true
					});
					item++;
				}
			}
		})
	}else{
		node.addChild({
			children: [{}],
	    });
	}
};

function createAddChildDialog(propertiesArray){
	var item = 0;
	if(true){
		this.addChildDialogCreated = true;
		$('.items').remove();
		while(record = propertiesArray[item]){
			$('#addChildDialog').append(
				$('<div></div>')
		  		    .attr("class", 'items')
			    	.css('margin-top', '10px')
					.append(
						$('<label style="display: block;float: left;width: 90px"></label>')
				  		.attr("id", record.name)
				    	.text(record.name)
				    	.css('margin-left', '10px')
					)
			    	.append(
			    		$('<input type="text">')
				    	.attr("id", record.postName)
				    	.addClass("citiesForm")
			    	)
			);
			if(record.type == 'number'){
				$('#' + record.name).keypress(function (e) {
				    if (String.fromCharCode(e.keyCode).match(/[^0-9]/g)) return false;
				});
			}
			item++;
		}
	}
	$('#mask').fadeIn();
	$('#addChildDialog').show();
};
function getTaskName(){
	var selectedNodeLevel = $("#tree").dynatree("getTree").activeNode.getLevel();	
	var taskName;
	switch (selectedNodeLevel){
		case 1:
		taskName = "addNewFacility"
		  break;
		case 2:
		taskName = "addNewDepartment"
		  break;
		case 3:
		taskName = "addNewRoom"
		  break;
		case 4:
		taskName = "addNewDevice"
		  break;
	}
	return taskName;
}


