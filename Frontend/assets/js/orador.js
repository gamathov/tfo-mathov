const NuevoOrador =()=>{
    const nombre=document.getElementById('nombre').value;
    const apellido=document.getElementById('apellido').value;
    const mail=document.getElementById('mail').value;
    const tema=document.getElementById('tema').value;
            
    const orador = {
        nombre,
        apellido,
        mail,
        tema
    };

    const respuesta = fetch ("http://localhost:8080/wep-app/controller",{
        method: "POST",
        body: JSON.stringify(orador)
    
});
    
    //intento resolver promesa
    respuesta
    .then(response => response.json())
    .then(respuesta => {
        console.log(respuesta)
    })
    .catch (error => console.log (error));
}
    document.getElementById('btnOrador').addEventListener('click',NuevoOrador);