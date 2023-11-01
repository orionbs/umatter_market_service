# Offre Service

### Projet : uMatter

## Requis

```yaml
Base de donnée: POSTGRES
```

## Variables d'environnement

```yaml
database:
  source:
    host: adresse IP du serveur Postgres
    port: port du serveur Postgres
    user: nom de l'utilisateur Postgres
    password: mot de passe de l'utilisateur Postgres
    database: nom de la base de donnée

http:
  security:
    issuer-url: l'url du fournisseur de token
```