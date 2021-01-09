
export default {
  getIniciarSesionUrl: () => '/iniciarsesion',
  getRegistrarseUrl: () => '/registrarse',
  getOlvidoContraseniaUrl: () => '/olvidocontrasenia',
  getNegocioPerfilUrl: (negocioId) => `/negocios/perfil/${negocioId}`,
  getClientePerfilUrl: () => '/clientes/perfil',
  getClienteComprarVoucherUrl: () => '/clientes/comprar',
  getClienteCanjearVoucherUrl: () => '/clientes/canjear',
  getClienteHistorialVoucherUrl: () => '/clientes/historial',
  getReclamosUrl: () => '/reclamos',
  getTalonariosUrl: () => '/talonarios',
  getVouchersConfirmablesUrl: () => '/negocios/vouchersconfirmables',
  getTalonariosCrearUrl: () => '/talonarios/crear',
  getProductosUrl: () => '/productos',
  getProductosCrearUrl: () => '/productos/crear',
}
