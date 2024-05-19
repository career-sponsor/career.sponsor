class CreateCompanies < ActiveRecord::Migration[7.1]
  def change
    create_table :companies do |t|
      t.string :name
      t.string :city
      t.string :county
      t.string :type
      t.string :route

      t.timestamps
    end
  end
end
