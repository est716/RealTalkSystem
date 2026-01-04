import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/welcomePage'
    },
    {
      path: '/welcomePage',
      name: 'WelcomePage',
      component: () => import('../WelComePage.vue')
    },
    {
      path: '/startPage',
      name: 'StartPage',
      component: () => import('../StartPage.vue')
    },
    {
      path: '/chatRoom/:roomId?',
      name: 'ChatRoom',
      component: () => import('../RoomPage.vue')
    }
  ],
})

export default router
