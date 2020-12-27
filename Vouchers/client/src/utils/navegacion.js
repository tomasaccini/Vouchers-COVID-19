
export default {
  getIniciarSesionUrl: () => '/iniciarsesion',
  getRegistrarseUrl: () => '/registrarse',
  getOlvidoContraseniaUrl: () => '/olvidocontrasenia',
  getNegocioPerfilUrl: (usuarioId) => `/negocios/perfil/${usuarioId}`,
  getClientePerfilUrl: () => '/clientes/perfil',
  getClienteComprarVoucherUrl: () => '/clientes/comprar',
  getClienteCanjearVoucherUrl: () => '/clientes/canjear',
  getReclamosUrl: () => '/reclamos',
  getTalonariosUrl: () => '/talonarios',
  getVouchersConfirmablesUrl: () => '/negocios/vouchersconfirmables',
  getTalonariosCrearUrl: () => '/talonarios/crear',
  getProductosUrl: () => '/productos',
  getProductosCrearUrl: () => '/productos/crear',
}
