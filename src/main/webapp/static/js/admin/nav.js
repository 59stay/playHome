var navs = [{
	"title" : "后台首页",
	"icon" : "icon-computer",
	"href" : "/page/main.html",
	"spread" : false
}
/*,{
	"title" : "文章列表",
	"icon" : "icon-text",
	"href" : "page/news/newsList.html",
	"spread" : false
},{
	"title" : "友情链接",
	"icon" : "icon-text",
	"href" : "page/links/linksList.html",
	"spread" : false
},{
	"title" : "404页面",
	"icon" : "&#xe61c;",
	"href" : "page/404.html",
	"spread" : false
}*/
,{
	"title" : "基础信息管理",
	"icon" : "&#xe61c;",
	"href" : "",
	"spread" : false,
	"children" : [
		{
			"title" : "用户信息",
			"icon" : "&#xe631;",
			"href" : "/admin/userInfo",
			"spread" : false
		},
		{
			"title" : "类别信息",
			"icon" : "&#xe631;",
			"href" : "/admin/dataDictionary",
			"spread" : false
		},
		{
			"title" : "友情链接信息",
			"icon" : "icon-text",
			"href" : "/admin/friendshipLink",
			"spread" : false
		}
	]
}
,{
	"title" : "资源管理",
	"icon" : "&#xe61c;",
	"href" : "",
	"spread" : false,
	"children" : [
		{
			"title" : "游戏资源",
			"icon" : "&#xe631;",
			"href" : "/admin/gameResource",
			"spread" : false
		},
		{
			"title" : "电影资源",
			"icon" : "&#xe631;",
			"href" : "",
			"spread" : false
		}
	]
}]