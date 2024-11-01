<template>
    <div v-if="error" class="error">
        {{ error }}
    </div>
</template>

<script>
export default {
    props: ['error']
}
</script>

<style>
.error {
    color: red;
}
</style>