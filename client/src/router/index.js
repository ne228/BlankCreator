import { createRouter, createWebHistory } from 'vue-router'

import LoginComponent from '@/modules/auth/LoginComponent.vue'
import RegisterComponent from '@/modules/auth/RegisterComponent.vue'
import Profile from '@/components/Profile.vue'
import HelloWorld from '@/components/HelloWorld.vue'
import CreateBlank from '@/components/blank/CreateBlank.vue'
import BlankInfo from '@/components/blank/BlankInfo.vue'
import Blanks from '@/components/blank/Blanks.vue'
import BlankHubs from '@/components/blank/BlankHubs.vue'
import BlankEdit from '@/components/blank/BlankEdit.vue'
import BlankHubCreate from '@/components/blank/BlankHubCreate.vue'
import BlankHubRed from '@/components/blank/BlankHubRed.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HelloWorld
    },
    {
      path: '/login',
      name: 'Login',
      component: LoginComponent
    },
    {
      path: '/register',
      name: 'Register',
      component: RegisterComponent
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile
    },
    {
      path: '/hub/:hubId/blank/create',
      name: 'Create',
      component: CreateBlank
    },
    {
      path: '/hub/:hubId/blank/:blankId',
      name: 'BlankInfo',
      component: BlankInfo
    },
    {
      path: '/hub/:hubId/blank/edit/:blankId',
      name: 'BlankEdit',
      component: BlankEdit
    },
    {
      path: '/hub/:id',
      name: 'Blanks',
      component: Blanks
    },
    {
      path: '/hub',
      name: 'BlankHubs',
      component: BlankHubs
    },
    {
      path: '/hub/create',
      name: 'BlankHubCreate',
      component: BlankHubCreate
    },
    {
      path: '/hub/edit/:id',
      name: 'BlankHubRed',
      component: BlankHubRed
    }
  ]
})

router.beforeEach((to, from, next) => {
  const publicPages = ['/login', '/register', '/home']
  const authRequired = !publicPages.includes(to.path)
  const loggedIn = localStorage.getItem('user')

  // trying to access a restricted page + not logged in
  // redirect to login page
  if (authRequired && !loggedIn) {
    localStorage.setItem('redirectPath', to.fullPath)
    console.log(localStorage.redirectPath)
    next('/login')
  } else {
    next()
  }
})
export default router
