package ru.netology

object CommentService : CRUDServiceRestorable<Comment> {
    private var items = mutableListOf<Comment>()
    private var uniqueId: Int = 0;

    fun reset() {
        uniqueId = 0;
        items = mutableListOf();
    }

    private fun getNextId(): Int {
        return ++uniqueId;
    }

    override fun add(element: Comment): Comment {
        val note = NoteService.getById(element.noteId) ?: throw NoteNotFoundException(element.id)
        val newElement = element.copy(id = getNextId(), note.id)
        items.add(newElement);
        return newElement
    }

    override fun getAll(): Collection<Comment> {
        return items
    }

    fun getByNoteId(noteId: Int): Collection<Comment> {
        return items.filterIndexed { index, item -> !item.isDeleted && item.noteId == noteId }
    }

    override fun getById(id: Int): Comment? {
        return items.firstOrNull { item -> item.id == id && !item.isDeleted }
    }

    override fun edit(id: Int, element: Comment): Comment {
        val curElement = getById(id) ?: throw CommentNotFoundException(id);
        val index = items.indexOf(curElement)
        val newElement = element.copy(id = curElement.id);
        items[index] = newElement
        return newElement;
    }

    override fun delete(id: Int): Comment {
        val curElement = getById(id) ?: throw CommentNotFoundException(id);
        val index = items.indexOf(curElement)
        val newElement = curElement.copy(isDeleted = true);
        items[index] = newElement
        return newElement;
    }

    override fun restore(id: Int): Comment {
        val curElement = items.firstOrNull { item -> item.id == id } ?: throw CommentNotFoundException(id);
        val index = items.indexOf(curElement)
        val newElement = curElement.copy(isDeleted = false);
        items[index] = newElement
        return newElement;
    }


}
