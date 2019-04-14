import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/Login'
import Admin from '@/views/Admin'
import Expired from '@/views/Expired'
import Unauth from '@/views/Unauth'
import IndexPage from '@/views/Index'
import SysUser from '@/views/system/User'
import SysAuth from '@/views/system/Auth'
import SysRole from '@/views/system/Role'
import SysLocation from '@/views/system/Location'
import SysOperLog from '@/views/system/Operation'
import Setting from '@/views/system/Setting'
import Carousel from '@/views/carousel/Carousel'
import Customer from '@/views/customer/Customer'
import Bespeak from '@/views/customer/Bespeak'
import Card from '@/views/customer/Card'
import SysConfig from '@/views/system/Config'
import Shop from '@/views/shop/Shop'
import Franchiser from '@/views/franchiser/Franchiser'
import Consultant from '@/views/consultant/Consultant'

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [{
            path: '/',
            redirect: '/login',
            meta: {
                isMenu: false
            }
        },
        {
            path: '/login',
            name: 'login',
            component: Login,
            meta: {
                title: '登录',
                isMenu: false
            }
        },
        {
            path: '/expired',
            name: 'expired',
            component: Expired,
            meta: {
                title: '会话过期',
                isMenu: false
            }
        },
        {
            path: '/unauth',
            name: 'unauth',
            component: Unauth,
            meta: {
                title: '无权限',
                isMenu: false
            }
        },
        {
            path: '/admin',
            redirect: '/index',
            meta: {
                title: '布局',
                isMenu: false,
                isLogin: true
            }
        },
        {
            path: '/home',
            name: 'admin',
            redirect: '/index',
            component: Admin,
            meta: {
                title: '首页',
                isMenu: true,
                isLeaf: true,
                isLogin: true
            },
            children: [{
                path: '/index',
                name: 'index',
                component: IndexPage,
                meta: {
                    title: '首页',
                    isMenu: false,
                    isLogin: true
                }
            }]
        },
        {
            path: '/system',
            name: 'system',
            component: Admin,
            meta: {
                title: '系统管理',
                isMenu: true,
                isLeaf: false,
                permission: 'system.user|system.role|system.auth|system.log|system.config|system.location'
            },
            children: [{
                    path: '/system/user',
                    name: 'user',
                    component: SysUser,
                    meta: {
                        title: '人员管理',
                        isMenu: true,
                        isLeaf: true,
                        permission: 'system.user'
                    }
                },
                {
                    path: '/system/role',
                    name: 'role',
                    component: SysRole,
                    meta: {
                        title: '角色管理',
                        isMenu: true,
                        isLeaf: true,
                        permission: 'system.role'
                    }
                },
                {
                    path: '/system/auth',
                    name: 'auth',
                    component: SysAuth,
                    meta: {
                        title: '权限管理',
                        isMenu: true,
                        isLeaf: true,
                        permission: 'system.auth'
                    }
                },
                {
                    path: '/system/location',
                    name: 'location',
                    component: SysLocation,
                    meta: {
                        title: '地区管理',
                        isMenu: true,
                        isLeaf: true,
                        permission: 'system.location'
                    }
                },
                {
                    path: '/system/log',
                    name: 'log',
                    component: SysOperLog,
                    meta: {
                        title: '系统日志',
                        isMenu: true,
                        isLeaf: true,
                        permission: 'system.log'
                    }
                },
                {
                    path: '/system/config',
                    name: 'config',
                    component: SysConfig,
                    meta: {
                        title: '系统参数',
                        isMenu: true,
                        isLeaf: true,
                        permission: 'system.config'
                    }
                }
            ]
        },
        {
            path: '/customer',
            name: 'customer',
            redirect: '/customer/index',
            component: Admin,
            meta: {
                title: '会员管理',
                isMenu: true,
                isLeaf: false,
                isLogin: true,
                permission: 'customer|card|bespeak'
            },
            children: [{
                path: '/card/index',
                name: 'cardPage',
                component: Card,
                meta: {
                    title: '会员卡',
                    isMenu: true,
                    isLeaf: true,
                    isLogin: true,
                    permission: 'card'
                }
            }, {
                path: '/customer/index',
                name: 'customerPage',
                component: Customer,
                meta: {
                    title: '会员信息',
                    isMenu: true,
                    isLeaf: true,
                    isLogin: true,
                    permission: 'customer'
                }
            }, {
                path: '/bespeak/index',
                name: 'bespeakPage',
                component: Bespeak,
                meta: {
                    title: '预约看车',
                    isMenu: true,
                    isLeaf: true,
                    isLogin: true,
                    permission: 'bespeak'
                }
            }]
        },
        {
            path: '/carousel',
            name: 'carousel',
            redirect: '/carousel/index',
            component: Admin,
            meta: {
                title: '轮播管理',
                isMenu: true,
                isLeaf: true,
                isLogin: true,
                permission: 'carousel'
            },
            children: [{
                path: '/carousel/index',
                name: 'carouselPage',
                component: Carousel,
                meta: {
                    title: '轮播管理',
                    isMenu: false,
                    isLeaf: true,
                    permission: 'carousel'
                }
            }]
        },
        {
            path: '/shop',
            name: 'shop',
            redirect: '/shop/index',
            component: Admin,
            meta: {
                title: '门店管理',
                isMenu: true,
                isLeaf: true,
                isLogin: true,
                permission: 'shop'
            },
            children: [{
                path: '/shop/index',
                name: 'shopPage',
                component: Shop,
                meta: {
                    title: '门店管理',
                    isMenu: false,
                    isLeaf: true,
                    permission: 'shop'
                }
            }]
        },
        {
            path: '/consultant',
            name: 'consultant',
            redirect: '/consultant/index',
            component: Admin,
            meta: {
                title: '汽车顾问',
                isMenu: true,
                isLeaf: true,
                isLogin: true,
                permission: 'consultant'
            },
            children: [{
                path: '/consultant/index',
                name: 'consultantPage',
                component: Consultant,
                meta: {
                    title: '汽车顾问',
                    isMenu: false,
                    isLeaf: true,
                    permission: 'consultant'
                }
            }]
        },
        {
            path: '/franchiser',
            name: 'franchiser',
            redirect: '/franchiser/index',
            component: Admin,
            meta: {
                title: '经销商',
                isMenu: true,
                isLeaf: true,
                isLogin: true,
                permission: 'franchiser'
            },
            children: [{
                path: '/franchiser/index',
                name: 'franchiserPage',
                component: Franchiser,
                meta: {
                    title: '经销商',
                    isMenu: false,
                    isLeaf: true,
                    permission: 'franchiser'
                }
            }]
        },
        {
            path: '/setting',
            name: 'setting',
            redirect: '/system/setting',
            component: Admin,
            meta: {
                title: '个人设置',
                isMenu: false,
                isLogin: true,
                permission: 'system.setting'
            },
            children: [{
                path: '/system/setting',
                name: 'SettingIndex',
                component: Setting,
                meta: {
                    title: '个人设置',
                    isMenu: false,
                    isLogin: true
                }
            }]
        }
    ]
})