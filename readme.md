Ce projet contien deux parties, une partie pour la génération des données et autre pour faire des traitements sur les données généré.

- 1er partie (génération des données):

package: tn.lansrod.carrefour
class : GenerateCarrefourData

Cette class prend comme exemple les paramètres suivants: 10/06/2019 /home/pathto/input 1200 20000 ou:

10/06/2019 est la date a partir de laquelle vous voulez genéré les données
/home/pathto/input est le shemin vers le dossier ou vous souhaitez stocké les données générer
1200 est le nombre des magasins que vous souhaitez crée
20000 est le total produit par magasin

Les données final sont ecrite sous /home/pathto/input de la façon suivante:

/home/pathto/input/20190610/ fichiers des ref et transaction
/home/pathto/input/20190611/ fichiers des ref et transaction
/home/pathto/input/20190612/ fichiers des ref et transaction
/home/pathto/input/20190613/ fichiers des ref et transaction etc ...

- 2eme partie (traitement des données):

Cette partie est découpé en plusieur sous partie selon le task que vous souhaité exécuter
chaque class ou chaque task prend comme exemple les paramètres suivants: 10/06/2019 | /home/pathto/input /home/pathto/output

10/06/2019 est la date en question Rq: mème si vous souhaité travailler sur toute une semaine on considère cette date comme date début
| est le délimiter des fichiers
/home/pathto/input est le shemin vers l'emplacement des données 
/home/pathto/output est le shemin ou vous souhaite ecrire votre resultat

package: tn.lansrod.carrefour
Task 1 class : ProcessTransactionTopHundredQteByDay
Task 2 class : ProcessTransactionTopHundredQteGlobalByDay
Task 3 class : ProcessTransactionTopHundredChiffreAffaireByDay
Task 4 class : ProcessTransactionTopHundredChiffreAffaireGlobalByDay
Task 5 class : ProcessTransactionTopHundredQteJ7
Task 6 class : ProcessTransactionTopHundredQteGlobalJ7
Task 7 class : ProcessTransactionTopHundredChiffreAffaireJ7

Les données final sont écrite sous /home/pathto/output de la façon suivante

/home/pathto/output/20190610/ fichiers des top_vent ...
