# Résumé du langage #

## Messages ##

### Descriptions ###
Les expressions message sont encadrées pas des crochets <br />
```
[receveur messsage];
```

Le receveur peut être:
  * Une variable ou une expression qui renvoie un objet (incluant la variable _self_)
  * Le nom d'une classe
  * _super_
Le message est le nom d'une méthode plus ses paramètres.

### Exemple ###
```
[myRectangle setWidth:20.0];
```


<br />
## Les nouveaux types ##

### Descriptions ###
| Type | Définition |
|:-----|:-----------|
| id   | ...        |
| Class | ...        |
| SEL  | ...        |
| IMP  | ...        |
| BOOL | Une valeur Boolean, soit _YES_ soit _NO_  |

### Exemples ###


<br />
## Directives du préprocesseur ##

### Descriptions ###
Le préprocesseur comprend ces notations:
| Notation | Définition |
|:---------|:-----------|
| _#import_ | Permet d'importer un fichier d'entête. Cette directive est identique à _#include_, sauf qu'elle inclut le fichier d'entête pas plus d'une fois. |
| _//_     | Début d'un commentaire de ligne. |

### Exemple ###
```
#import "Rectangle.h"; // Permet d'importer le fichier d'entête "Rectangle.h"
```


<br />
## Directives du compilateur ##
### Descriptions ###
Les directives de compilation commencent par "@". Les directives suivant sont utilisées pour déclarer ou définir des classes, catégories et protocoles:

| Directive | Définition |
|:----------|:-----------|
| @interface |            |
| @implementation |            |
| @protocol |            |
| @end      |            |

Les directives suivantes (qui s'excluent mutuellement) permettent de spécifier la visibilité des variables d'instance:
| Directive | Définition |
|:----------|:-----------|
| @private  | ...        |
| @protected | ...        |
| @public   | ...        |

Par défaut elles sont _@protected_.

Les directives de gestion des exceptions:
| Directive | Définition |
|:----------|:-----------|
| @try      | ...        |
| @throw    | ...        |
| @catch    | ...        |
| @finally  | ...        |

Directives de propriétés:
| Directive | Définition |
|:----------|:-----------|
| @property | ...        |
| @synthesize | ...        |
| @dynamic  | ...        |

Les autres directives:
| Directive | Définition |
|:----------|:-----------|
| @class    | ...        |
| @selector(nom\_méthode) | ...        |
| @protocol(nom\_protocole) | ...        |
| @"string" | ...        |
| @"string1" @"string2" ... @"stringN" | ...        |
| @synchronized() | ...        |


<br />
## Classes ##
Les classes sont déclarées grâce à _@interface_ directive.
```
#import "ItsSuperclass.h"

@interface ClassName : ItsSuperclass <protocol_list> {
    // déclarations des variables
    type var1; // protected
@private
    type var2; // private
@protected
    type var3; // protected
@public
    type var4; // public
} 

// déclarations des méthodes
@end
```

Le fichier contenant les définitions des méthodes:
```
#import "ClassName.h"

@implementation ClassName
    // définitions des méthodes
@end
```

<br />
## Catégories ##
La déclaration d'une catégorie relativement similaire à celle d'une classe.
```
#import "ClassName.h"
@interface ClassName ( CategoryName ) < protocol list >
    // déclarations des méthodes
@end
```

<br />
## Format des Protocols ##


<br />
## Déclarations de méthode ##


<br />
## L'implémentations de méthode ##


<br />
## Désapprobation syntaxique ##


<br />
## Conventions de nommage ##