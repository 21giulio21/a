import sqlite3

class DB:

    #Passo il path nel costruttore
    def __init__(self,pathDatabase):
        self.conn = sqlite3.connect(pathDatabase)
        self.cursore = self.conn.cursor()

    def executeQuery(self,query):
        print query
        self.cursore.execute(query)



    def fine(self):
        self.conn.commit()