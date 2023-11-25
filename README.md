# Book Realm

## Decisions made
- I allowed the user to input future dates for the date published, in case they have a copy of a book that is yet to be published.
- The term "date modified" is kind of vague, so I took the liberty to only set it when editing the "core properties" of a book (title, author, date published, and total pages), and not when modifying the flags or the reading progress. I.e., it only changes when using the edit ✏️ feature of the app.

### Missing features
- Toast messages to confirm successful data manipulation
- Hiding of the FABs on the archives screen when there are no archived books
(I will fix these tomorrow)
