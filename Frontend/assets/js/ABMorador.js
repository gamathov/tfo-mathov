//PARA EDITAR ORADORES

//variables para guardar el id del orador a modificar y el array de oradores

let oradorId;
let oradores =[];

const setOradores = (nuevosOradores) => {
  oradores = nuevosOradores;
  };


function listarNuevosOradores() {
  const respuesta = fetch(`http://localhost:8080/wep-app/controller`);
  respuesta
      .then(response => response.json())
      .then(oradoresDesdeBD => {
          oradores = oradoresDesdeBD;
          setOradores(oradores);
      })
      .catch(error => dibujarError(error));
}

listarNuevosOradores();

//Guardo el id correspondiente al orador de la fila del botón precionado 

const setId = (id) => {
    oradorId = id;  
    const orador = oradores.find(o => o.id === oradorId);

    // Relleno los campos del modal con los datos del orador
    document.getElementById('modalNombre').value = orador.nombre;
    document.getElementById('modalApellido').value = orador.apellido;
    document.getElementById('modalMail').value = orador.mail;
    document.getElementById('modalTema').value = orador.tema;
  
    // Para mostrar el modal
    //const miModal = new bootstrap.Modal(document.getElementById('exampleModal'));
    //miModal.show();
}       
    // Función para guardar cambios del orador desde el modal
    const guardarCambiosOrador = () => {
    // Obtener datos del modal
    const idOrador = oradorId; 
    const nombre = document.getElementById('modalNombre').value;
    const apellido = document.getElementById('modalApellido').value;
    const mail = document.getElementById('modalMail').value;
    const tema = document.getElementById('modalTema').value;
  
    // Crear objeto con datos actualizados
    const oradorActualizado = {
      nombre,
      apellido,
      mail,
      tema
    };
  
    // Llamar a la función actualizarOrador con los datos actualizados
    actualizarOrador(idOrador, oradorActualizado);
  
  };

  // Función para realizar la solicitud PUT al servidor
    const actualizarOrador = (id, oradorActualizado) => {
    const url = `http://localhost:8080/wep-app/controller?id=${id}`;
  
    fetch(url, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(oradorActualizado),
    })
      .then(response => {
        if (!response.ok) {
          throw new Error(`Error al actualizar el orador: ${response.statusText}`);
        }
        return response.json();
      })
    .then(oradorActualizado => {
      console.log('Orador actualizado:', oradorActualizado);

 
      listarOradores();})
  };
//PARA CREAR ORADOR
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

    listarOradores();

//PARA LISTAR ORADORES

function listarOradores() {
//DEFINO EL CONTROLADOR DESDE DONDE SE OBTENDRÁN LOS DATOS
const respuesta = fetch(`http://localhost:8080/wep-app/controller`);

//2 invocar
respuesta
    .then(response => response.json())
    .then(data => procesarListado(data))//fulfilled
    .catch(error => dibujarError(error))//rejected
}
function procesarListado(data) {
const listarOradores = data;
let rows = '';
for(let orador of listarOradores) {
    console.log(orador);
    rows += `
    <tr>
        <th scope="row">${orador.id}</th>
        <td>${orador.nombre}</td>
        <td>${orador.apellido}</td>
        <td>${orador.mail}</td>
        <td>${orador.tema}</td>
        <td>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="setId(${orador.id})">
        Editar
      </button>
            <button type="button" class="btn btn-danger" onclick="eliminarOrador(${orador.id})">
                Eliminar
            </button>
        </td>
    </tr>
    `
}
document.getElementById('usersRows').innerHTML = rows;
}

function dibujarError(error) {
console.log(error);
const alerta = `<div class="alert alert-danger" role="alert">
    ${error.toString()}
</div>`;
document.getElementById('msj').innerHTML = alerta;
}

document.getElementById('btnGetUsers').addEventListener('click',listarOradores);

//ELIMINAR ORADOR
eliminarOrador = (id) => {

    fetch(`http://localhost:8080/wep-app/controller?id=${id}`, {
        method: "DELETE",
    })
    .then(response => response) 
    .then(json => {
        alert(`se ha eliminado el orador id: ${id}`);
        listarOradores();
    })
    .catch(err => console.log(err));
}

