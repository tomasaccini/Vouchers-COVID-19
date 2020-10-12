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
