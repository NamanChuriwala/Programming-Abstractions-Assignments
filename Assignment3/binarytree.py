class BinaryTree:
    def __init__(self, value=None):
        self.data = value
        self.left = None
        self.right = None

    @property
    def data(self):
        return self.__data

    @data.setter
    def data(self, value):
        self.__data = value

    def add_leftchild(self, tree):
        if (tree.data == None) or (type(tree.data) == type(self.data)):
            self.left = tree
        else:
            raise TypeError("Type mismatch between " + str(type(self.data)) + " and " + str(type(tree.data)))

    def add_rightchild(self, tree):
        if (tree.data == None) or (type(tree.data) == type(self.data)):
            self.right = tree
        else:
            raise TypeError("Type mismatch between " + str(type(self.data)) + " and " + str(type(tree.data)))

    def __iter__(self):
        if self:
            yield self.data 
        if self.left is not None:
            for d in self.left:
                yield d
        if self.right is not None:
            for d in self.right:
                yield d
