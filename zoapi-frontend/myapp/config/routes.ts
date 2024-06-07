export default [
  { path: '/', name:"主页" , icon: "smile", component: './Index' },
  // 动态路由
  { path: '/interface_info/:id', name:"查看接口" , icon: "smile", component: './InterfaceInfo' , hideInMenu:true },
  {
    path: '/user',
    layout: false,
    routes: [{ name: '登录', path: '/user/login', component: './User/Login' }],
  },
  {
    path: '/admin',
    name: '管理页',
    icon: 'crown',
    // ant design 的设定规则,在access.ts中
    access: 'canAdmin',
    // 子路由
    routes: [
      { path: '/admin/interface_analysis', name: '接口分析', component: './Admin/InterfaceAnalysis' },
      { path: '/admin/interface_info', name: '接口管理', component: './Admin/InterfaceInfo' },
    ],
  },
  { path: '*', layout: false, component: './404' },
];
