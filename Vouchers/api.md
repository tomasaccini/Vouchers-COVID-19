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

* URL/clients/{id}

```md
* Devuele al cliente
```

#### Respuesta

```json
{
    fullName: <nombre completo del cliente>
    phoneNumber: <número de teléfono>
    email: <email>
}
```

## Negocio

### URL/businesses/{id}

#### Params

* URL/businesses/{id}

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
* Devuelve el voucher modificado, con estado retirado
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

## Tarifario

### URL/tarifario/{id}

#### Params

* URL/tarifario/{id}

```md
* Devuelve el tarifario con el id indicado
```

#### Respuesta

```json
{
    id: <long:id>
    stock: <int:cantidad disponible para comprar>
    cantidadVendida: <int:cantidad que ya se vendió>
    nombre: <string:nombre del negocio dueño del tarifario>
    info: {
        precio: <long: precio de un voucher>
        descripcion: <string: descripcion del tarifario>
        validoDesde: <date>
        validoHasta: <date>
        items: <listado de los items que lo componen>
    }
}
```

### URL/tarifarios

* URL/tarifarios
* URL/tarifarios?max=n

```md
* Devuele lista de todos los tarifarios, excepto que se indique un máximo
```

#### Respuesta

```json
{
    [lista de tarifarios con los atributos mostrados en show]
}
```

### URL/tarifarios/search

#### Params

* URL/tarifarios/search?q={búsqueda}
* URL/tarifarios/search?q={búsqueda}&max={máx resultado}

```md
* Por razones de tiempos (más que nada en un escenario real) la búsqueda no se realiza si la cadena buscada es de 2 o menos caracteres.
* Devuelve un listado de los tarifario que poseen la cadena buscada en su descripción.
```

#### Respuesta

```json
{
    listado de tarifario, ver formato en show
}
