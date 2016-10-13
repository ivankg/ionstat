$(document).ready(function() {
	$("#addChildButton").hide();
	$('#downloadReportButton').hide();
	$('#addNewSessionButton').hide();
	$('#deleteSession').hide();
	$('#approveDeclineAction').hide();
});

function creteAvailableActions(availbleActions, selectedSession) {
	if (selectedSession) {
	}else {
		$("#addChildButton").hide();
		$('#downloadReportButton').hide();
		$('#addNewSessionButton').hide();
		$('#deleteSession').hide();
		$('#approveDeclineAction').hide();
	}
	var item = 0;
	var currentAction;
	while(currentAction = availbleActions[item]){
		if(currentAction == 'addNewDepartment' || currentAction == 'addNewRoom' || currentAction == 'addNewDevice' || currentAction == 'addNewFacility'){
			var task = "";
			switch (currentAction)
			{
			case 'addNewFacility':
			  text = "Add Facility";
			  break;
			case 'addNewDepartment':
			  text = "Add Department";				  
			  break;
			case 'addNewRoom':
			  text = "Add Room";			  
			  break;
			case 'addNewDevice':
			  text = "Add Device";			 
			  break;
			}
			   $('#addChildButton').linkbutton({
				   text: text
			   });
		       $("#addChildButton").show();
		}else{
			switch (currentAction)
			{
			case 'createSession':
			  $('#addNewSessionButton').show();
			  break;
			case 'downloadReport':
			  $('#downloadReportButton').show();			  
			  break;
			case 'deleteSession':
		      $('#deleteSession').show();		  
			  break;
			case 'approveSession':
		      $('#approveDeclineAction').show();		  
			  break;
			}
		}
		item++;
	}

}