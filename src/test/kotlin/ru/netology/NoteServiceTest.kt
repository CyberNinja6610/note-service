package ru.netology

import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    private fun getTestNote(): Note {
        val id = 0
        val isDeleted = false
        val title = "Заголовок"
        val text = "Сообщение"

        return Note(id = id, isDeleted = isDeleted, title = title, text = text);
    }

    private fun addTestNote(): Note {
        val note = getTestNote()
        return NoteService.add(note);
    }

    private fun addTestComment(noteId: Int): Comment {
        val message = "Комментарий";
        val comment = Comment(message = message, noteId = noteId)
        return CommentService.add(comment)
    }

    @Test
    fun getAll() {
        NoteService.reset()
        val notes = NoteService.getAll()
        assertEquals(0, notes.size)
    }

    @Test
    fun getAll_empty() {
        NoteService.reset()
        CommentService.reset()

        addTestNote();
        val notes = NoteService.getAll()
        assertEquals(1, notes.size)
    }

    @Test
    fun add() {
        NoteService.reset()
        CommentService.reset()

        val addedNote = addTestNote();
        assertNotEquals(0, addedNote.id)
    }

    @Test
    fun getById() {
        NoteService.reset()
        CommentService.reset()

        val addedNote = addTestNote()
        val foundedNote = NoteService.getById(addedNote.id);

        assertEquals(addedNote.id, foundedNote?.id)
    }

    @Test
    fun getById_notFound() {
        NoteService.reset()
        CommentService.reset()
        addTestNote()

        val fakeId = 0;
        val foundedNote = NoteService.getById(fakeId);
        assertNull(foundedNote)
    }

    @Test
    fun edit() {
        NoteService.reset();
        CommentService.reset();

        val addedNote = addTestNote()

        val id = 0
        val isDeleted = false
        val title = "Новый заголовок"
        val text = "Новое сообщение"

        val newNote = addedNote.copy(id = id, isDeleted = isDeleted, title = title, text = text)
        val editedNote = NoteService.edit(addedNote.id, newNote)

        assertEquals(addedNote.id, editedNote.id)
    }

    @Test
    fun edit_notFound() {
        assertThrows(NoteNotFoundException::class.java) {
            NoteService.reset();
            CommentService.reset();
            val addedNote = addTestNote()

            val id = 0
            val isDeleted = false
            val title = "Новый заголовок"
            val text = "Новое сообщение"
            val fakeId = 0;

            val newNote = addedNote.copy(id = id, isDeleted = isDeleted, title = title, text = text)
            NoteService.edit(fakeId, newNote)
        }
    }

    @Test
    fun delete() {
        NoteService.reset();
        CommentService.reset();

        val addedNote = addTestNote()

        val deletedNote = NoteService.delete(addedNote.id)

        assertTrue(deletedNote.isDeleted)
    }

    @Test
    fun delete_notFound() {
        assertThrows(NoteNotFoundException::class.java) {
            NoteService.reset();
            CommentService.reset();

            val addedNote = addTestNote()
            val fakeId = 0;

            NoteService.delete(fakeId)

        }
    }

    @Test
    fun delete_witComment() {
        NoteService.reset();
        CommentService.reset();
        val addedNote = addTestNote();

        val addedComment = addTestComment(addedNote.id)

        NoteService.delete(addedNote.id)
        val deletedComment = CommentService.getById(addedComment.id)
        assertNull(deletedComment)

    }
}