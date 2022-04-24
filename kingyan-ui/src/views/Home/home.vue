<template>
  <h1>Home</h1>
  <div>
    <el-link type="info">all roles</el-link>
    <br> <br>
    <el-link type="primary">admin</el-link>
    <br> <br>
    <el-link type="success">test</el-link>
  </div>
  <br> <br> <br>
  <el-input
    v-model="textarea"
    disabled
    :rows="3"
    :clos="30"
    type="textarea"
    placeholder="请求结果"
    :input-style="{width:'500px'}"
  />
  <br>
  <br>
  <router-link to="/login">请先登录</router-link>
  <br>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import service from '@/util/http'

const textarea = ref('')
const text = reactive({ value: '' })
text.value = '请求结果'

const user = ref()

service.get('auth/getUserInfo').then(r => { user.value = r })
console.log(user.value)
</script>
