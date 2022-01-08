package com.dewerro.myproductsapp.di

import android.app.Application
import androidx.room.Room
import com.dewerro.myproductsapp.my_products.data.data_source.Database
import com.dewerro.myproductsapp.my_products.data.repository.CategoriesRepositoryImpl
import com.dewerro.myproductsapp.my_products.data.repository.ProductsRepositoryImpl
import com.dewerro.myproductsapp.my_products.domain.repository.CategoriesRepository
import com.dewerro.myproductsapp.my_products.domain.repository.ProductsRepository
import com.dewerro.myproductsapp.my_products.domain.use_case.CategoriesUseCase
import com.dewerro.myproductsapp.my_products.domain.use_case.ProductsUseCases
import com.dewerro.myproductsapp.my_products.domain.use_case.categories.AddCategory
import com.dewerro.myproductsapp.my_products.domain.use_case.categories.DeleteCategory
import com.dewerro.myproductsapp.my_products.domain.use_case.categories.GetCategories
import com.dewerro.myproductsapp.my_products.domain.use_case.categories.UpdateCategory
import com.dewerro.myproductsapp.my_products.domain.use_case.products.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideProductsDatabase(app: Application): Database {
        return Room.databaseBuilder(
            app,
            Database::class.java,
            Database.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductsRepository(db: Database): ProductsRepository {
        return ProductsRepositoryImpl(db.productsDao)
    }

    @Provides
    @Singleton
    fun provideProductsUseCases(repository: ProductsRepository): ProductsUseCases {
        return ProductsUseCases(
            getProducts = GetProducts(repository),
            addProduct = AddProduct(repository),
            deleteProduct = DeleteProduct(repository),
            updateProduct = UpdateProduct(repository),
            getProductByCategoryName = GetProductByCategoryName(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCategoriesRepository(db: Database): CategoriesRepository {
        return CategoriesRepositoryImpl(db.categoriesDao)
    }

    @Provides
    @Singleton
    fun provideCategoriesUseCase(repository: CategoriesRepository): CategoriesUseCase {
        return CategoriesUseCase(
            getCategories = GetCategories(repository),
            addCategory = AddCategory(repository),
            deleteCategory = DeleteCategory(repository),
            updateCategory = UpdateCategory(repository)
        )
    }
}