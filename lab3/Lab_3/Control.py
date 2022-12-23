T = 100
FILE_NAME = 'data/MOCK_DATA.txt'
from Model import *
from tkinter import messagebox


class Control:
    def __init__(self, view):
        self.__view = view
        self.__BTree = BTree(T)
        self.open_file()

    def open_file(self):
        try:
            file = open(FILE_NAME, "rt")
            data = file.read()
            data = data.strip()
            data = data.split('\n')
            for i in range(0, len(data)):
                k = data[i][1:-1].split(', ')
                self.__BTree.insert([int(k[0]), k[1][1:-1]])
            file.close()
            self.__view.set_label(f"File {FILE_NAME} was successfully opened")
        except IOError:
            messagebox.showerror("Error", f"File {FILE_NAME} can not be open")

    def submit(self, action, key, value):
        try:
            key = int(key)
        except ValueError:
            action = 0
            messagebox.showerror('Error', "Key is not right")
        match action:
            case 1:
                self.__search(key)
            case 2:
                self.__add(key, value)
            case 3:
                self.__delete(key)
            case 4:
                self.__edit(key, value)
            case _:
                pass

    def __search(self, key):
        value = self.__BTree.search(key)
        if value is not None:
            self.__view.set_key(key)
            self.__view.set_value(value)
            self.__view.set_label(f"Key: {key} Value: {value}")
        else:
            self.__view.set_label("There is no such value")
            self.__view.set_value("There is no such value")

    def __add(self, key, value):
        value = value.strip()
        if value:
            res = self.__BTree.search(key)
            if not res:
                self.__BTree.insert([key, value])
                self.__view.set_label("Element was added")
            else:
                messagebox.showwarning("Warning", "The key is exists")
        else:
            messagebox.showwarning("Warning", "Field 'Value' is  empty")

    def __delete(self, key):
        res = self.__BTree.search(key)
        if res:
            self.__BTree.delete(self.__BTree.root, [key, ])
            self.__view.set_label(f"Element {key} was successfully deleted")
        else:
            messagebox.showwarning("Warning", "There is no such key")

    def __edit(self, key, value):
        value = value.strip()
        if value:
            res = self.__BTree.search(key)
            if res:
                self.__BTree.edit(key, value)
                self.__view.set_label("Element was successfully changed")
            else:
                messagebox.showwarning("Warning", "There is no such key")
        else:
            messagebox.showwarning("Warning", "Field 'Value' is  empty")

    def save_file(self):
        self.__BTree.all_values = list()
        self.__BTree.save_data(self.__BTree.root)
        with open(FILE_NAME, "wt") as file:
            file.writelines("%s\n" % i for i in self.__BTree.all_values)
        self.__view.set_label("DB is successfully saved")
