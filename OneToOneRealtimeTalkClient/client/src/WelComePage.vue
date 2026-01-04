<template>
    <div class="container">
        <div class="card">
            <div class="welcome-page">
                <h1>微</h1>
                <h1>觀</h1>
                <h1>世</h1>
                <h1>界</h1>
                <h1>！</h1>
            </div>
        </div>
    </div>
</template>

<style scoped>
/* --- 容器與配色基礎 --- */
.container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    /* 保持您提供的背景樣式 */
    background: linear-gradient(135deg, #f0f4f8, #d9e2ec);
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.card {
    background-color: white;
    padding: 3.5rem 4rem;
    /* 增加 padding 讓文字有足夠的空間傾斜 */
    border-radius: 16px;
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.12);
    text-align: center;
}

/* --- 文字排版：水平且平均分布 (Flexbox) --- */
.welcome-page {
    display: flex;
    flex-direction: row;
    align-items: top;
    justify-content: space-between;
    width: 500px;
    /* 增加寬度以拉長弧度，使傾斜效果更自然 */
    height: 80px;
}

h1 {
    margin: 0;
    padding: 0 5px;
    color: #334e68;
    font-size: 3rem;
    font-weight: 800;
    line-height: 1;
    text-shadow: 2px 2px 3px rgba(0, 0, 0, 0.1);

    /* 設置 transform 的原點在文字的底部中心，確保旋轉時不會亂跑 */
    transform-origin: bottom center;
}

/* --- 實現弧度 + 傾斜 (∩型) --- */

/* 微 (最左側，最低點) */
.welcome-page h1:nth-child(1) {
    /* 垂直位移 (弧線) */
    transform: translateY(5px)
        /* 旋轉 (傾斜，向右傾斜 10 度) */
        rotate(-10deg);
    font-size: 3.3rem;
}

/* 觀 (次左側，次高點) */
.welcome-page h1:nth-child(2) {
    /* 垂直位移 (弧線) */
    transform: translateY(-30px)
        /* 旋轉 (傾斜，向右傾斜 5 度) */
        rotate(-5deg);
    font-size: 3.3rem;
}

/* 世 (中間，最高點，基準線拉高) */
.welcome-page h1:nth-child(3) {
    /* 垂直位移 (弧線) */
    transform: translateY(-40px)
        /* 旋轉 (不傾斜) */
        rotate(0deg);
    font-size: 3.3rem;
}

/* 界 (次右側，次高點) */
.welcome-page h1:nth-child(4) {
    /* 垂直位移 (弧線) */
    transform: translateY(-30px)
        /* 旋轉 (傾斜，向左傾斜 5 度) */
        rotate(5deg);
    font-size: 3.3rem;
}

/* ! (最右側，最低點) */
.welcome-page h1:nth-child(5) {
    /* 垂直位移 (弧線) */
    transform: translateY(5px)
        /* 旋轉 (傾斜，向左傾斜 10 度) */
        rotate(10deg);
    font-size: 4.3rem;
    color: #486581;
}
</style>
