package ru.netology

object NoteService : CRUDService<Note> {
    private var items = mutableListOf<Note>()
    private var uniqueId: Int = 0;

    fun reset() {
        uniqueId = 0;
        items = mutableListOf();
    }

    private fun getNextId(): Int {
        return uniqueId++;
    }

    override fun add(element: Note): Note {
        val newElement = element.copy(id = getNextId())
        items.add(newElement);
        return newElement
    }

    override fun getAll(): Collection<Note> {
        return items.filterIndexed { index, item -> !item.isDeleted }
    }

    override fun getById(id: Int): Note? {
        return items.firstOrNull { item -> item.id == id && !item.isDeleted }
    }

    override fun edit(id: Int, element: Note): Note {
        val curElement = getById(id) ?: throw NoteNotFoundException(id);
        val index = items.indexOf(curElement)
        val newElement = element.copy(id = curElement.id);
        items[index] = newElement
        return newElement;
    }

    override fun delete(id: Int): Note {
        val curElement = getById(id) ?: throw NoteNotFoundException(id);
        val index = items.indexOf(curElement)
        val newElement = curElement.copy(isDeleted = true);
        items[index] = newElement
        CommentService.getByNoteId(newElement.id).forEach {
            CommentService.delete(it.id);
        }
        return newElement;
    }
}
