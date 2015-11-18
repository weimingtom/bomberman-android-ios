# Description du fonctionnement du développement sur iOS #

## Présentation ##

### SDK iPhone ###
| Xcode | L’outil de développement Apple, il permet la création de projets iPhone, l’édition du code source Objective-C, la compilation et le débogage des applications |
|:------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Interface Builder | C’est un outil visuel pour construire des interfaces graphiques.                                                                                              |
| Organizer | Cet outil vous permet de gérer les iPhone que vous utilisez pour développer et d’y installer des applications sans passer par iTunes.                         |
| iPhone Simulator | C’est un simulateur permettant de tester les applications directement sur l’ordinateur.                                                                       |
| Instruments | Cet outil permet d’analyser un programme pour surveiller l’état de la mémoire, l’utilisation du réseau, du CPU, ...                                           |
| Shark | Il permet d’optimiser l’application en identifiant les fonctions dans lesquelles elle passe le plus de temps.                                                 |

### Documentation Apple ###
  * [iOS Dev Center](http://developer.apple.com/devcenter/ios/index.action)
  * Xcode -> Help -> Documentation

## Description d'un projet ##
| Classes | Contient tous les classes du projet: fichiers d'en tête (_.h_) et les fichiers sources (_.m_). Il est possible créer des sous dossiers pour organiser le projet. |
|:--------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Other Sources | Contient les autres fichiers sources du projet qui ne sont pas basés sur des classes en Objective-C (ce dossier nous sera probablement car nous allons coder l'application avec des classes Objective-C. <br /> Ce dossier contient par défaut le fichier main.m qui sera le point d'entrée de notre application |
| Ressources | Dans ce dossier, il y aura tous les ressources que notre application a besoin pour fonctionner. Notamment les fichiers de description de l'interface (_.xib_), le fichier permettant de décrire l'application (_Info.plist_) et les images qui seront affichées par l'application et notamment l'image _Default.png_ qui sera afficher au lancement de l'application (splash screen) pour donner l'illution que l'application est déjà chargée.  |
| Frameworks | Contient la liste des frameworks utilisés par notre application.                                                                                                 |
| Products | Ce dernier dossier contient les éléments générés par le compilateur.                                                                                             |


## Les fichiers ##

### Les différents types de fichiers ###
  * _.h_: fichiers d'en-tête
  * _.m_: fichiers source
  * _.xib_: fichiers de description de l'interface graphique
  * _.nib_:
  * _.plist_ (property list): fichiers xml


### Principaux fichiers ###
  * _main.m_: contient la fonction main() classique d'un programme en C, c'est le point d'entrée de notre application.
  * _Info.plist_: décrit l'application, voici les principales propriétés:
    1. _CFBundleIconFile_: permet d'indiquer le nom de l'icône (png de 57x57pixels)  de l'application, si la propriété n'est pas indiquée, le fichier Icon.png sera cherché.
    1. _UIPrerenderedIcon_: permet de rajouter le halo lumineux et les bords arrondis sont ajoutés automatiquement par iPhone.
    1. _CFBundleDisplayName_: permet d'indiquer le titre de l'application