# API - Wiki

## Producto

### URL/products/{id}

#### Params

* URL/products/{id}

```md
* Devuele el producto
```

#### Respuesta

```json
{
    name: <nombre del producto>
    description: <breve descripción>
    type: <>
}
```

### URL/products/getByBusiness

#### Params

* URL/products/getByBusiness/{businessId}
* URL/products/getByBusiness/{businessId}?max=n

```md
* Devuele todos los productos del negocio indicado por su id
* Se puede o no especificar una cant max a devolver
* URL/products/getByBusiness/businessId?max=n -> Cuando la cantidad máximas se especifica
```

#### Respuesta

```json
{lista de productos como se ve en el previo json}
```

## Cliente

### URL/clients/{id}

#### Params

* URL/clientes/{id}

```md
* Devuele al cliente
```

#### Respuesta

```json
{
    nombreCompleto: <nombre completo del cliente>
    numeroTelefono: <número de teléfono>
    email: <email>
}
```

## Negocio

### URL/negocios/{id}

#### Params

* URL/negocios/{id}

```md
* Devuele al negoocio
```

#### Respuesta

```json
{
    name: <string:nombre>
    phone_number: <string:número de teléfono>
    website: <string:página del sitio si la posee>
}
```

### URL/businesses

#### Params

* URL/businesses
* URL/businesses?max=n

```md
* Devuele lista de todos los negocios, excepto que se indique un máximo
```

#### Respuesta

```json
{
    [lista de negocios con los atributos mostrados en show]
}
```

## Voucher

### URL/voucher/canjear

#### Params

* post URL/voucher/canjear?clienteId={id}&voucherId={id}

```md
* Por razones de chequeo se indica tanto el voucher a canjear como el id del cliente dueño de este
* Devuelve el voucher modificado, con estado a confirmar
```

#### Respuesta

```json
{
    voucher, ver formato en show
}
```

### URL/voucher/confirmar

#### Params

* post URL/voucher/confirmar?negocioId={id}&voucherId={id}

```md
* Por razones de chequeo se indica tanto el voucher a confimar como el id del negocio dueño de este
* Devuelve el voucher modificado, con estado 'Canjeado'
```

#### Respuesta

```json
{
    voucher, ver formato en show
}
```

### URL/voucher/search

#### Params

* URL/voucher/search?q={búsqueda}
* URL/voucher/search?q={búsqueda}&max={máx resultado}

```md
* Por razones de tiempos (más que nada en un escenario real) la búsqueda no se realiza si la cadena buscada es de 2 o menos caracteres.
* Devuelve un listado de los vouchers que poseen la cadena buscada en su descripción.
```

#### Respuesta

```json
{
    listado de vouchers, ver formato en show
}
```

## Talonario

### URL/talonario/{id}

#### Params

* URL/talonario/{id}

```md
* Devuelve el talonario con el id indicado
```

#### Respuesta

```json
{
    id: <long:id>
    stock: <int:cantidad disponible para comprar>
    cantidadVendida: <int:cantidad que ya se vendió>
    nombre: <string:nombre del negocio dueño del talonario>
    info: {
        precio: <long: precio de un voucher>
        descripcion: <string: descripcion del talonario>
        validoDesde: <date>
        validoHasta: <date>
        items: <listado de los items que lo componen>
    }
}
```

### URL/talonarios

* URL/talonarios
* URL/talonarios?max=n

```md
* Devuele lista de todos los talonarios, excepto que se indique un máximo
```

#### Respuesta

```json
{
    [lista de talonarios con los atributos mostrados en show]
}
```

### URL/talonarios/search

#### Params

* URL/talonarios/search?q={búsqueda}
* URL/talonarios/search?q={búsqueda}&max={máx resultado}

```md
* Por razones de tiempos (más que nada en un escenario real) la búsqueda no se realiza si la cadena buscada es de 2 o menos caracteres.
* Devuelve un listado de los talonario que poseen la cadena en:
 - nombre de alguno de sus productos
 - descripcion de alguno de sus productos
 - descripcion del talonario
```

#### Respuesta

```json
{
    listado de talonario, ver formato en show
}
```

### URL/talonarios/comprar

Se crea un nuevo voucher a partir del talonario dado y se lo asigna al cliente indicado

#### Params

* post URL/talonarios/comprar
    con body {talonarioId: {id}, clienteId: {id}}

#### Respuesta

```json
 responde con el voucher creado, en caso de éxito
```
