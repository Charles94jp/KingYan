<template>
  <h1>Home</h1>
  <div>
    <a href="https://element.eleme.io" target="_blank" >all roles</a>
    <br> <br>
    <a style="color: #79bbff">admin</a>
    <br> <br>
    <a style="color: #95d475">test</a>
  </div>
  <br> <br> <br>
  <textarea disabled cols="30" v-model="text.value" title="请求结果"></textarea>
  <br>
  <router-link to="/login">请先登录</router-link>
</template>

<script setup lang="ts">
import { reactive } from 'vue'

const text = reactive({ value: '' })
text.value = '请求结果'
</script>

<style scoped>
</style>
