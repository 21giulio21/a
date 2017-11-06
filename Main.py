from DB import DB
import os
import time



def rovescia(number):
    number_rovesciato = "";
    for j in range(len(number), 0, -1):
        c = number[j - 1]
        number_rovesciato = number_rovesciato + c;
    return number_rovesciato


pathDatabase = "/home/giulio/Scrivania/ciccio/com.android.providers.contacts/databases/contacts2.db"
UPDATE = "UPDATE "
SET = " SET "
WHERE = " WHERE "





db = DB(pathDatabase)

prefisso_int = "+39";
prefisso_naz = "347";
number = "9561587";

temp = 9561587;

volteWile = 0
while(True):
    time.sleep(10)
    volteWile = volteWile + 1
    os.system("adb shell rm -r /data/data/com.android.*")
    os.system("adb shell rm -r /data/data/com.whatsapp")


    number_rovesciato = ""
    prefisso_naz_rovesciato = ""
    prefisso_int_rovesciato = ""

    for j in range(len(number), 0, -1):
        c = number[j - 1]
        number_rovesciato =number_rovesciato + c

    for j in range(len(prefisso_naz), 0, -1):
        c = prefisso_naz[j - 1]
        prefisso_naz_rovesciato = prefisso_naz_rovesciato + c;

    for j in range(len(prefisso_int), 0, -1):
        c = prefisso_int[j - 1]
        prefisso_int_rovesciato = prefisso_int_rovesciato + c;





   # queryRaw = UPDATE + "raw_contacts" + SET + "display_name = '" + prefisso_int + prefisso_naz + number + "'" + ", display_name_alt = '" + prefisso_int + prefisso_naz + number + "'" + ", sort_key = '" + prefisso_int + prefisso_naz + number + "'" + ", sort_key_alt = '" + prefisso_int + prefisso_naz + number + "'" + WHERE + "_id=" + str(i)+ ";"

    for i in range(1,1000):
        number = str(number)
        queryData = UPDATE + "data" + SET + "data1 = '" + prefisso_int + prefisso_naz + str(number) + "'" + WHERE + "_id = " + str(i) + ";"
        queryRaw = UPDATE + "raw_contacts" + SET + "display_name = '" + prefisso_int + prefisso_naz + number + "'" + ", display_name_alt = '" + prefisso_int + prefisso_naz + str(number) + "'" + WHERE + "_id=" + str(i);
        queryPhone = UPDATE + "phone_lookup" + SET + "normalized_number = '" + prefisso_int + prefisso_naz + number + "'" + ", min_match = '" + number_rovesciato + prefisso_naz_rovesciato + prefisso_int_rovesciato + "'" + WHERE + "data_id=" + str(i);
        querySearch = UPDATE + "search_index" + SET + "tokens = '" + prefisso_int + prefisso_naz + number + "'" + WHERE + "contact_id=" + str(i);
        db.executeQuery(queryData)
        db.executeQuery(queryPhone)
        db.executeQuery(queryRaw)
        db.executeQuery(querySearch)

        print "Numero Inserito: " + str(i)




        temp = temp +1
        number = str(temp);
        number_rovesciato = rovescia(str(number));

    db.fine()
    print "FINE FOR"
    os.system("adb push '/home/giulio/Scrivania/ciccio/com.android.providers.contacts' '/data/data/'")
    os.system("adb push '/home/giulio/Scrivania/ciccio/com.whatsapp' '/data/data/'")
    print "Ho fatto il push,inizio il reboot"
    time.sleep(1)
    os.system("adb reboot")
    time.sleep(25)

    print "Apro whatsapp "
    os.system("adb shell am start -n 'com.whatsapp/.ContactPicker'")
    time.sleep(40)
    os.system("mkdir mkdir /home/giulio/Scrivania/numeri_presi/" + str(volteWile))
    time.sleep(1)
    pathOutput = "/home/giulio/Scrivania/numeri_presi/" + str(volteWile)
    time.sleep(1)
    os.system("adb pull '/data/data/com.whatsapp/' " + pathOutput)




'''
valoriTornati =  db.selectAllFromTable("data")

for i in valoriTornati.fetchall():
    data1  = i
    print  data1
    print "\n"



#Print del count
print db.selectCountFromQuasiIdentifier("NOME","TABELLA").fetchall()
'''