<template>
    <div>
        <h1>
            <span id = 'titleImage' v-on:click="gotoEnter"></span>
            DaSa2
            <button id="logoutBtn" @click.prevent="onClickLogout">l.o</button>
        </h1>
    </div>
</template>

<script>
    export default {
        name: "Title",
        methods :{
            gotoEnter : function () {
                this.$router.push('/enter')
                    .catch(err=>{
                        console.log(err);
                    })
            },
            onClickLogout : function(){
                this.$store.dispatch('LOGOUT').then(() => this.$router.push('/'))
            }
        }
    }
</script>

<style scoped>

    h1{
        color:#2F3B52;
        font-weight: 900;
        margin: 5rem 0 1.5rem;
    }
    @media screen and (min-width: 768px) and (min-height: 900px){
       h1{
           margin: 10rem 0 1.5rem;
       }
    }

    #titleImage{
        text-align: center;
        display: inline-block;
        width: 50px;
        height: 50px;
        box-shadow: 0 3px 6px rgba(0,0,0,.2), 0 3px 6px rgba(0,0,0,.2);
        border-radius: 30px 30px;
        background-size: cover;
        margin-right: 1.1rem;
        background-image: url('../assets/kimdami.png');
    }

    #titleImage:hover{
        cursor: pointer;
    }

    #logoutBtn{
        margin-left: 1.1rem;
    }

</style>