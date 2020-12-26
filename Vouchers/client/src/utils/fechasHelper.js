
export default {
  extraerHoraYMinutos: (fechaStr) => fechaStr.slice(11, 16),
  extraerAnioMesDia: (fechaStr) => fechaStr.slice(0, 10),
}
