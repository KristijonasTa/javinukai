import React from 'react';

function CategoryPreview({ category, clickRemoveCategory }) {
  const { name, description, totalSubmissions } = category;

  return (
    <div className="category-preview">
      <h2>{name}</h2>
      <p>Description: {description}</p>
      <p>Total Submissions: {totalSubmissions}</p>
      <button
        className="categoryPreviewContainerDeleteButton"
        onClick={() => clickRemoveCategory(category)}
      >
        -
      </button>
    </div>
  );
}

export default CategoryPreview;
