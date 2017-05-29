(function (window) {
	'use strict';

	// Your starting point. Enjoy the ride!
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
					suceess:function(){
						alert("성공"+text);
					}
				});					
			}
		}
	});
})(window);
