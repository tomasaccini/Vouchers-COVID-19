function formatearStrings(texto, historiaDeUsuario, ts) {
  return `${texto}_${historiaDeUsuario}_${ts}`
}

module.exports = {
  formatearStrings
};