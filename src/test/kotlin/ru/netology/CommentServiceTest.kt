package ru.netology

import org.junit.Test

import org.junit.Assert.*

class CommentServiceTest {

    private fun getTestNote(): Note {
        val id = 0
        val isDeleted = false
        val title = "Заголовок"
        val text = "Сообщение"

        return Note(id = id, isDeleted = isDeleted, title = title, text = text);
    }

    private fun addTestNote(): Note {
        val item = getTestNote()
        return NoteService.add(item);
    }

    private fun addTestComment(noteId: Int): Comment {
        val message = "Комментарий";
        val comment = Comment(message = message, noteId = noteId)
        return CommentService.add(comment)
    }

    @Test
    fun getAll() {
        val items = CommentService.getAll()
        assertNotNull(items)
    }

    @Test
    fun add() {
        NoteService.reset()
        CommentService.reset()

        val addedNote = addTestNote()
        val addedComment = addTestComment(addedNote.id)
        assertNotEquals(0, addedComment.id)
    }

    @Test
    fun getById() {
        NoteService.reset()
        CommentService.reset()

        val addedNote = addTestNote()
        val addedComment = addTestComment(addedNote.id)
        val foundedComment = CommentService.getById(addedComment.id);

        assertEquals(addedComment.id, foundedComment?.id)
    }

    @Test
    fun getById_notFound() {
        NoteService.reset()
        CommentService.reset()
        addTestNote()

        val addedNote = addTestNote()
        addTestComment(addedNote.id)
        val fakeId = 0;
        val foundedComment = CommentService.getById(fakeId);
        assertNull(foundedComment)
    }

    @Test
    fun edit() {
        NoteService.reset();
        CommentService.reset();

        val addedNote = addTestNote()

        val message = "Новое сообщение"

        val addedComment = addTestComment(addedNote.id)
        val newComment = addedComment.copy(message = message, id = 0)
        val editedComment = CommentService.edit(addedNote.id, newComment)

        assertEquals(addedComment.id, editedComment.id)
    }

    @Test
    fun edit_notFound() {
        assertThrows(CommentNotFoundException::class.java) {
            NoteService.reset();
            CommentService.reset();

            val addedNote = addTestNote()

            val message = "Новое сообщение"

            val addedComment = addTestComment(addedNote.id)
            val newComment = addedComment.copy(message = message, id = 0)
            CommentService.edit(id = 0, newComment)
        }
    }

    @Test
    fun delete() {
        NoteService.reset();
        CommentService.reset();

        val addedNote = addTestNote()

        val addedComment = addTestComment(addedNote.id)
        val deletedComment = CommentService.delete(addedComment.id)

        assertTrue(deletedComment.isDeleted)
    }

    @Test
    fun delete_notFound() {
        assertThrows(CommentNotFoundException::class.java) {
            NoteService.reset();
            CommentService.reset();

            val addedNote = addTestNote()
            addTestComment(addedNote.id)
            CommentService.delete(0)


        }
    }

    @Test
    fun restore() {
        NoteService.reset();
        CommentService.reset();

        val addedNote = addTestNote()

        val addedComment = addTestComment(addedNote.id)
        val deletedComment = CommentService.delete(addedComment.id)
        val restoredComment = CommentService.restore(deletedComment.id)

        assertFalse(restoredComment.isDeleted)
    }

    @Test
    fun restore_notFound() {
        assertThrows(CommentNotFoundException::class.java) {
            NoteService.reset();
            CommentService.reset();

            val addedNote = addTestNote()

            val addedComment = addTestComment(addedNote.id)
            val deletedComment = CommentService.delete(addedComment.id)
            val restoredComment = CommentService.restore(0)
        }
    }
}