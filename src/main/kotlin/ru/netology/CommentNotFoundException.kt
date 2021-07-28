package ru.netology

import java.lang.Exception

class CommentNotFoundException(id: Int) : Exception("no comment with id $id")