(function (window) {
	'use strict';

	// Your starting point. Enjoy the ride!
	$.ajax({
		method:'GET',
		url:"/api/todos",
		success:function(data){				
			var tag = "";
			$.each(data, function(index, value){
				if(value.completed == 1)
					tag += "<li class='completed'>";
				else
					tag += "<li>"
				tag += "<div class='view'>";
				tag += "<input type='hidden' class='todoid' value='"+value.id+"'/>";
				tag += "<input class='toggle' type='checkbox'>";
				tag += "<label>"+value.todo+"</label>";
				tag += "<button class='destroy'></button>";
				tag += "</div>";
				tag += "<input class='edit' value='Create a TodoMVC template'>";
				tag += "</li>";
			});
			$(".todo-list").append(tag);		
		}
	});
	
	$(".new-todo").keydown(function(key){
		if(key.keyCode == 13){
			var text = $(this).val();
			if(text != ''){
				$.ajax({
					method:'POST',
					url:"/api/todos",
					data:{
						"todo" : text
					},
					statusCode:{
						201:function(data){
							var tag = "<li>"
							tag += "<div class='view'>";
							tag += "<input type='hidden' class='todoid' value='"+data.id+"'/>";
							tag += "<input class='toggle' type='checkbox'>";
							tag += "<label>"+data.todo+"</label>";
							tag += "<button class='destroy'></button>";
							tag += "</div>";
							tag += "<input class='edit' value='Create a TodoMVC template'>";
							tag += "</li>";
							$(".todo-list").prepend(tag);
						}
					}
				});					
			}
		}
	});

	$(document).on("click", ".toggle", function(){
		$(this).parent().parent().addClass("completed");
		var id = $(this).parent().find('.todoid').val();
		console.log(id+" / " + $(this).parent().find("label").html());
		
	});
})(window);
