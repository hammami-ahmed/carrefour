Ce projet contient deux parties, une partie pour la génération des données et une autre pour faire des traitements sur les données générées.

#1ère partie (génération des données):

#package: tn.lansrod.carrefour
#class : GenerateCarrefourData

Cette classe prend comme exemple les paramètres suivants: 10/06/2019 /home/pathto/input 1200 20000 ou:

10/06/2019 est la date a partir de laquelle vous voulez genérer les données
/home/pathto/input est le chemin vers le dossier ou vous souhaitez stocker les données générées
1200 est le nombre des magasins que vous souhaitez crée
20000 est le total produit par magasin

Les données finales sont écrites sous /home/pathto/input de la façon suivante:

/home/pathto/input/20190610/ fichiers des ref et transaction etc ...

#2eme partie (traitement des données):

Cette partie est découpée en plusieurs sous parties selon le task que vous souhaiez exécuter
chaque classe ou chaque task prend comme exemple les paramètres suivants: 10/06/2019 | /home/pathto/input /home/pathto/output

10/06/2019 est la date en question Rq: mème si vous souhaitez travailler sur toute une semaine on considère cette date comme date début
| est le délimiter des fichiers
/home/pathto/input est le chemin vers l'emplacement des données 
/home/pathto/output est le chemin ou vous souhaitez ecrire votre resultat

#package: tn.lansrod.carrefour
#Task 1 class : ProcessTransactionTopHundredQteByDay
#Task 2 class : ProcessTransactionTopHundredQteGlobalByDay
#Task 3 class : ProcessTransactionTopHundredChiffreAffaireByDay
#Task 4 class : ProcessTransactionTopHundredChiffreAffaireGlobalByDay
#Task 5 class : ProcessTransactionTopHundredQteJ7
#Task 6 class : ProcessTransactionTopHundredQteGlobalJ7
#Task 7 class : ProcessTransactionTopHundredChiffreAffaireJ7

#StartTasksSequentially: une class qui lance tous les tasks sequentiellement

Les données finales sont écrites sous /home/pathto/output de la façon suivante

/home/pathto/output/Task number/20190610/ fichiers des top_vent ...
