from DB import DB
import os
import time



def rovescia(number):
    number_rovesciato = "";
    for j in range(len(number), 0, -1):
        c = number[j - 1]
        number_rovesciato = number_rovesciato + c;
    return number_rovesciato


pathDatabase = "/home/giuliofisso/Scrivania/Whaz NUMERI/CARTELA CONTATTI/com.android.providers.contacts/databases/contacts2.db"
UPDATE = "UPDATE "
SET = " SET "
WHERE = " WHERE "





db = DB(pathDatabase)

prefisso_int = "+39";
prefisso_naz = "340";
number = "1100000";

temp = 1100000;

volteWile = 10
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

    for i in range(1,10001):
        queryData = UPDATE + "data" + SET + "data1 = '" + prefisso_int + prefisso_naz + number + "'" + WHERE + "_id = " + str(i) + ";"
        queryRaw = UPDATE + "raw_contacts" + SET + "display_name = '" + prefisso_int + prefisso_naz + number + "'" + ", display_name_alt = '" + prefisso_int + prefisso_naz + number + "'" + WHERE + "_id=" + str(i);
        queryPhone = UPDATE + "phone_lookup" + SET + "normalized_number = '" + prefisso_int + prefisso_naz + number + "'" + ", min_match = '" + number_rovesciato + prefisso_naz_rovesciato + prefisso_int_rovesciato + "'" + WHERE + "data_id=" + str(i);
        querySearch = UPDATE + "search_index" + SET + "tokens = '" + prefisso_int + prefisso_naz + number + "'" + WHERE + "contact_id=" + str(i);
        db.executeQuery(queryData)
        db.executeQuery(queryPhone)
        db.executeQuery(queryRaw)
        db.executeQuery(querySearch)

        print "Numero Inserito: " + str(i)




        temp = temp + 1
        number = str(temp);
        number_rovesciato = rovescia(number);
    db.fine()

    print "FINE FOR"
    os.system("adb push '/home/giuliofisso/Scrivania/Whaz NUMERI/CARTELA CONTATTI/com.android.providers.contacts'  '/data/data/'")
    os.system("adb push '/home/giuliofisso/Scrivania/Whaz NUMERI/WHAZ DA MANDARE/com.whatsapp' '/data/data/'")
    print "Ho fatto il push,inizio il reboot"
    time.sleep(1)
    os.system("adb reboot")
    time.sleep(25)

    print "Apro whatsapp "
    os.system("adb shell am start -n 'com.whatsapp/.ContactPicker'")
    time.sleep(400)
    os.system("mkdir '/home/giuliofisso/Scrivania/Whaz NUMERI/numeri_presi/'" + str(volteWile))
    time.sleep(1)
    pathOutput = "'/home/giuliofisso/Scrivania/Whaz NUMERI/numeri_presi/'" + str(volteWile)
    time.sleep(1)
    os.system("adb pull '/data/data/com.whatsapp/' " + pathOutput)




