function post(url,param,callback){
	var xhr
	try{
		xhr=new XMLHttpRequest();//对象
	}catch(e){
		xhr=new ActiveXobject("xxxxxx");
	}
	xhr.onreadystatechange=function(){
		if(xhr.readyState==4&&xhr.status==200){
		callback(xhr.responseText);
		}
	}
	xhr.open("POST",url)
	//如果是文件上传方式，请不要设置Content-type，因为太复杂
	if(!(param instanceof FormData)){
		xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	}
	xhr.send(param);
}