@/*
    头像参数的说明:
    name : 名称
    id : 头像的id
@*/

    @if(isEmpty(avatarImg)){
          <img src="${ctxPath}/static/img/sso.png"/>
    @}else{
          <img src="${ctxPath}/kaptcha/${avatarImg}"/>
    @}


