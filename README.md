# Rapport de Projet Spring - Gestion de Plats et Menus

## Fonctionnalités Demandées et Implémentées

### Gestion des Entités

#### CRUD sur Plat

 - ✅ Création, lecture, mise à jour et suppression des plats.
 - ✅ Système de filtrage et pagination pour l’affichage des plats :
 - ✅ Système de filtrage optionnel pour les menus :
    - Tri par calories minimum, ou maximum.
    - Tri par catégorie (en fonction de celles qui existent en base de données)

#### CRUD sur Menu

 - ✅ Création, lecture, mise à jour et suppression des menus.
 - ✅ Calcul automatique des calories totales d’un menu (somme des calories de ses plats).
 - ✅ Affichage détaillé des menus (liste des plats associés et description) en cliquant sur la ligne qui représente un menu.
 - ✅ Système de filtrage optionnel pour les menus :
    - Tri par tranche de prix.
    - Tri par nom.

#### Édition des Menus

 - ✅ Interface ergonomique pour l’ajout/suppression de plats dans un menu :
 - Recherche de plats par nom, catégorie ou calories.
 - Filtres dynamiques (catégorie, calories min/max) lors de la sélection.
 - Affichage en temps réel des calories totales lors de la modification.

## Fonctionnalités Bonus Ajoutées

 - Ajout de notifications pour la modification, l’ajout, la suppression et en cas d’erreur (Si on essaye d’éditer une entité qui n’existe pas) comme vu en cours
 - Calcul du nombre total de catégories pendant l’édition d’un plat
 - Page d’accueil très jolie
 - Ajout d’un bouton pour le thème sombre et le thème clair
 - Ajout d’un graphique fait avec chartjs pour voir la répartition des calories en fonction des glucides, lipides et protéines, j’ai rajouté une catégorie autre  si la quantité de calories additionnée n’est pas la même que la quantité de calories en base. Les couleurs du texte des labels et de la légende sont adaptés au thème lors de la création (Après avoir changé de thème il faut recharger pour modifier les graphiques existants)
 - Sécurité sur les formulaires d’édition/création, pas de valeurs négatives, sur les calories, lipides, glucides, protéines, le prix, les noms ne doivent pas être trop courts ou trop longs.
 - Affichage d’un message si il n’y a pas de plats/menus dans la base de données.
