
export default {
  getIniciarSesionUrl: () => '/iniciarsesion',
  getRegistrarseUrl: () => '/registrarse',
  getOlvidoContraseniaUrl: () => '/olvidocontrasenia',
  getNegocioPerfilUrl: (usuarioId) => `/negocios/perfil/${usuarioId}`,
  getClientePerfilUrl: () => '/clientes/perfil',
  getClienteComprarVoucherUrl: () => '/clientes/comprar',
  getClienteCanjearVoucherUrl: () => '/clientes/canjear',
  getReclamos: () => '/reclamos',
  getTalonarios: () => '/talonarios',
  getTalonariosCrear: () => '/talonarios/crear',
  getProductos: () => '/productos',
  getProductosCrear: () => '/productos/crear',
}
