package com.davidsunday.meal_assessment.data.local.db

import com.davidsunday.meal_assessment.data.remote.Category
import com.davidsunday.meal_assessment.data.remote.Tag

class AppDatabase {

    private var categories = listOf<Category>()
    private var tags = listOf<Tag>()

    fun setCategories(categories: List<Category>) {
        this.categories = categories
    }

    fun getCategories(): List<Category> {
        return categories
    }

    fun setTags(tags: List<Tag>) {
        this.tags = tags
    }

    fun getTags(): List<Tag> {
        return tags
    }
}
