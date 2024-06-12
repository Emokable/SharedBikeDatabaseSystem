<template>
    <button class="toggle-button" @click="handleClick">
        <span id="toogleDark" class="toggle-icon"></span>
    </button>
</template>

<script setup lang="ts">
import { useDark, useToggle } from '@vueuse/core'
const isDark = useDark()
const toggleDark = useToggle(isDark)

const handleClick = () => {
    toggleDark()
}
</script>

<style scoped>
.toggle-button {
    height: 1.5rem;
    width: 2.5rem;
    border-radius: 9999px;
    border: 1px solid #4B5563;
    padding-left: 1px;
    transition: background-color 0.3s ease;
}

.toggle-icon {
    display: block;
    width: 1.25rem;
    height: 1.25rem;
    transform: translateX(0);
    border-radius: 9999px;
    background-color: #4B5563;
    transition: transform 0.3s ease;
}

body.dark .toggle-button {
    border-color: #9CA3AF;
}

body.dark .toggle-icon {
    transform: translateX(1rem);
    background-color: #9CA3AF;
}
</style>