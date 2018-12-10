# clean-crud
Example of reactive-crud following uncle Bob's [clean-architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) style

![alt text](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

#### Build
`$ ./gradlew clean build`

---

#### Run
`$ ./gradlew bootRun`

It will start a [flapdoodle](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo) embedded mongo database locally, listening on a random port


---
#### Endpoints

Retrieve all books: `GET localhost:8080/books`

Retrieve book by id: `GET localhost:8080/books/{id}`

Create book: `POST localhost:8080/books`

    {
        "title": "1984",
        "category": "FICTION",
        "author": {
            "fullName": "George Orwell"
        },
        "year": 1949
    }

Update book: `PUT localhost:8080/books`
    
    {
        "id":"5c0d82ddd7524043aeec084f"
        "title": "1984",
        "category": "FICTION",
        "author": {
            "fullName": "Eric Arthur Blair"
        },
        "year": 1949
    }

Remove book: `DELETE localhost:8080/books/{id}`