$(document).ready(function() {
	$('#loginDialog')
		.append(
				$('<div></div>')
		    	.text('User name')
		    	.css('margin-left', '10px')
		    	.css('margin-top', '50px')
		    	.append(
		    		$('<input type="text">')
			    	.css('margin-left', '10px')
			    	.attr("id", 'userName')
		    	)
		)
		.append(
				$('<div></div>')
		    	.text('Password')
		    	.css('margin-left', '10px')
		    	.css('margin-top', '20px')
		    	.append(
		    		$('<input type="text">')
			    	.css('margin-left', '18px')
			    	.attr("id", 'password')
		    	)
		)
		.append(
					$('<input>')
						.attr('id', 'loginSubmit')
						.attr('type', 'submit')
				    	.css('margin-left', '210px')
				    	.css('margin-top', '20px')
						.css('width', '50px')
						.css('height', '30px')
				)
		$('#loginDialog').show()
		$("#loginSubmit").click(function(button) {
			var userName = $("#userName")[0].value
			var password = $("#password")[0].value
			$.ajax({
				type : "GET",
				url : 'login.is',
				data : {userName:JSON.stringify(userName), password:JSON.stringify(password)},
				dataType : "json",
				success : function(data) {
				}	
			});
		})
})