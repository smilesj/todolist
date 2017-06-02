(function (window) {
	'use strict';

	// Your starting point. Enjoy the ride!
	getReadAll();
	
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
							getCount();
						}
					}
				});					
			}
		}
	});

	$(document).on("click", ".toggle", function(){
		var id = $(this).parent().find('.todoid').val();
		var element = $(this);
		$.ajax({
			method:'PUT',
			url:"/api/todos/"+id,
			statusCode:{
				204:function(){
					element.parent().parent().addClass("completed");
					element.prop("checked", true);
					getCount();
				}
			}
		});
	});
	
	$(document).on("click", ".destroy", function(){
		var id = $(this).parent().find('.todoid').val();
		var element = $(this).parent().parent();
		deleteTodo(element, id);
	});
	
	$('.filters li a').click(function(event){
	    event.preventDefault();
	    $('.filters li a').removeClass("selected");
	    $(this).addClass("selected");
	    var filter = $(this).html().toLowerCase();
	    getReadAll("/"+filter);
	});
	
	$(document).on("click", ".clear-completed", function(){
		$(".todo-list>li").each(function(index){
			if($(this).hasClass("completed")){
				var element = $(this);
				var id = $(this).find(".todoid").val();
				deleteTodo(element, id);
			}
		});
		
	});
})(window);

function getReadAll(filter){
	filter = typeof filter !== 'undefined' ? filter : '';
	if(filter == '/all')
		filter = '';
	$.ajax({
		method:'GET',
		url:"/api/todos"+filter,
		success:function(data){	
			var tag = "";
			$.each(data, function(index, value){
				if(value.completed == 1)
					tag += "<li class='completed'>";
				else
					tag += "<li>"
				tag += "<div class='view'>";
				tag += "<input type='hidden' class='todoid' value='"+value.id+"'/>";
				if(value.completed == 1)
					tag += "<input class='toggle' type='checkbox' checked>"
				else
					tag += "<input class='toggle' type='checkbox'>";
				tag += "<label>"+value.todo+"</label>";
				tag += "<button class='destroy'></button>";
				tag += "</div>";
				tag += "<input class='edit' value='Create a TodoMVC template'>";
				tag += "</li>";
			});
			$(".todo-list").empty();
			$(".todo-list").append(tag);
			getCount();
		}
	});
}

function getCount(){
	$.ajax({
		method:'GET',
		url:"/api/todos/count",
		success:function(data){
			$(".todo-count").find("strong").html(data);
		}
	});
}

function deleteTodo(element, id){
	$.ajax({
		method:'DELETE',
		url:"/api/todos/"+id,
		statusCode:{
			204:function(){
				element.remove();
				getCount();
			}
		}
	});
}
