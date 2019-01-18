/**用户登录事件*/
function showLogin(){
	layer.open({
	  type: 2,  //layer提供5种层类型，可传入的值有：0（信息框，默认）、1（页面层）、2（ifream层）、3（加载层）、4（tips层）若你采用layer.open({type: 1})方式调用，则type为必填项（信息框除外）
	  title: '用户登录',
	  area: ['300px', '400px'],
	  content: '/user/userLogin.html' //iframe的url
	}); 
}

/**用户注册事件*/
function showRegister(){
	layer.open({
		  type: 2,
		  title: '用户注册',
		  area: ['400px', '560px'],
		  content: '/user/userRegister.html' //iframe的url
		}); 
}
/**
 * 修改失效资源链接
 * @param id
 */
function modifyLink(id){
	layer.open({
		  type: 2,
		  title: '修复资源分享链接',
		  area: ['700px', '260px'],
		  content: '/user/common/modifyShareLink.html?id='+id //iframe的url
		});
}

/**
 * 刷新父页面
 */
function reloadPage(){
	window.location.reload();
}


/**图片等比例缩放*/
function ResizeImages()
{
   var myimg,oldwidth,oldheight;
   var maxwidth=800;
   var maxheight=1000
   var imgs = document.getElementById('content').getElementsByTagName('img');   //获取指定document中的图片
   for(i=0;i<imgs.length;i++){
     myimg = imgs[i];
     if(myimg.width > myimg.height)
     {
         if(myimg.width > maxwidth)
         {
            oldwidth = myimg.width;
            myimg.height = myimg.height * (maxwidth/oldwidth);
            myimg.width = maxwidth;
         }
     }else{
         if(myimg.height > maxheight)
         {
            oldheight = myimg.height;
            myimg.width = myimg.width * (maxheight/oldheight);
            myimg.height = maxheight;
         }
     }
   }
}